/*
 * Copyright 2026 Untold Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.untoldstudio.rimeui.core.ui.task;

public final class LoopTask extends Task {
    long nextRunTimeMillis;
    int remainingFrequency;
    final long intervalMillis;
    final Runnable task;

    public LoopTask(long startWaitMillis, long intervalMillis, int remainingFrequency, Runnable task) {
        this.intervalMillis = intervalMillis;
        this.remainingFrequency = remainingFrequency;
        this.task = task;
        this.nextRunTimeMillis = System.nanoTime() / 1_000_000 + startWaitMillis;
    }

    @Override
    public boolean tryRun(long currentTimeMillis) {
        if (currentTimeMillis >= nextRunTimeMillis) {
            if (remainingFrequency <= 0) {
                return true;
            }
            nextRunTimeMillis = currentTimeMillis + intervalMillis;
            remainingFrequency--;
            setNeedResort(true);
            try {
                task.run();
            } catch (Exception e) {
                throw new RuntimeException("A fatal error occurred in the task!", e);
            }
            return remainingFrequency <= 0;
        }
        return false;
    }
}
