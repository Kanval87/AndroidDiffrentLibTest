package com.volley.swastik.retrofitJob;


import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.volley.swastik.App;
import com.volley.swastik.retrofitJob.future.Future;
import com.volley.swastik.retrofitJob.future.FutureOnUiThread;
import com.volley.swastik.utils.JobKeyUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import de.greenrobot.event.EventBus;

public abstract class PromiseJob<PromiseResult> extends Job {
    private static final ConcurrentHashMap<String, WeakReference<PromiseJob>> jobHashMap = new ConcurrentHashMap<>();
    private final Future callerFuture;
    private String key;
    private final List<Future<PromiseResult>> futureList = new ArrayList<>();
    private static final Object jobHashMapLockObject = new Object();
    private PromiseResult promiseResult;

    public PromiseJob(Params params) {
        super(params);
        throw new IllegalArgumentException("Wrong super called for single instance job.");
    }

    public PromiseJob(Params params, Future<PromiseResult> future) {
        super(params);
        callerFuture = future;
    }

    private static PromiseJob jobForKey(String key) {
        WeakReference<PromiseJob> jobWeak = PromiseJob.jobHashMap.get(key);
        return (jobWeak == null) ? jobWeak.get() : null;
    }

    @Override
    public void onRun() throws Throwable {
        PromiseJob existingJob = jobForKey(generateKey());
        PromiseJob jobToUse = existingJob != null ? existingJob : this;

        if (callerFuture != null) {
            jobToUse.futureList.add(callerFuture);
        }

        if (jobToUse == this) {
            synchronized (jobHashMapLockObject) {
                jobHashMap.put(generateKey(), new WeakReference<PromiseJob>(this));
            }
        } else {
//            LightSailLog.i(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] skipped; already running.", generateKey()));
        }

        boolean jobAllowedToExecute = jobToUse == this;

        if (!jobAllowedToExecute) return;

//        LightSailLog.i(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] starting.", generateKey()));
        long startTime = System.currentTimeMillis();
        execute();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        float totalTimeSeconds = totalTime / 1000.0f;
//        LightSailLog.i(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] completed after %1.2f sec.", generateKey(), totalTimeSeconds));

        handleCompletion();

        synchronized (jobHashMapLockObject) {
            PromiseJob.jobHashMap.remove(generateKey());
        }
    }

    private void handleCompletion() {
        if (promiseResult == null) {
//            LightSailLog.e(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] completed without assigning promiseResult", generateKey()));
            return;
        }

        EventBus.getDefault().post(promiseResult);

        for (final Future<PromiseResult> future : futureList) {
            if (future instanceof FutureOnUiThread) {
                if (future.isPending()) {
                    App.getInstance().getForegroundActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (future.isPending()) {
                                future.onCallCompleted(PromiseJob.this.promiseResult);
                            }
                        }
                    });
                }
            } else {
                if (future.isPending()) {
                    future.onCallCompleted(PromiseJob.this.promiseResult);
                }
            }
        }
    }

    protected abstract void execute();

    private String generateKey() {
        if (key == null) {
            key = JobKeyUtil.keyForJob(this);
        }
        return key;
    }

    @Override
    protected void onCancel() {
        synchronized (jobHashMapLockObject) {
            PromiseJob.jobHashMap.remove(generateKey());
        }
    }
}
