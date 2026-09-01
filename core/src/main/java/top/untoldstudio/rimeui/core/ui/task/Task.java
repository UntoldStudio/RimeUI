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

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract sealed class Task implements Comparable<Task> permits NormalTask, DelayTask, LoopTask {
    private final AtomicBoolean isCanceled = new AtomicBoolean(false);
    protected boolean isNeedResort = false;

    public abstract boolean tryRun(long currentTimeMillis);

    public void setCanceled(boolean isCanceled) {
        this.isCanceled.set(isCanceled);
    }

    public void setNeedResort(boolean needResort) {
        this.isNeedResort = needResort;
    }

    public boolean isCanceled() {
        return isCanceled.get();
    }

    public void cancel() {
        setCanceled(true);
    }

    public boolean isNeedResort() {
        return isNeedResort;
    }

    @Override
    public int compareTo(@NotNull Task otherTask) {
        switch (this) {
            case NormalTask self -> {
                if (otherTask instanceof NormalTask other) {
                    return Long.compare(self.seq, other.seq);
                }
                return -1;
            }
            case DelayTask self -> {
                switch (otherTask) {
                    case NormalTask ignored -> {
                        return 1;
                    }
                    case DelayTask other -> {
                        return Long.compare(self.targetTimeMillis, other.targetTimeMillis);
                    }
                    case LoopTask other -> {
                        int result = Long.compare(self.targetTimeMillis, other.nextRunTimeMillis);
                        return result != 0 ? result : -1;
                    }
                    default -> {
                    }
                }
            }
            case LoopTask self -> {
                switch (otherTask) {
                    case NormalTask ignored -> {
                        return 1;
                    }
                    case DelayTask other -> {
                        int result = Long.compare(self.nextRunTimeMillis, other.targetTimeMillis);
                        return result != 0 ? result : 1;
                    }
                    case LoopTask other -> {
                        return Long.compare(self.nextRunTimeMillis, other.nextRunTimeMillis);
                    }
                    default -> {
                    }
                }
            }
            default -> {
            }
        }
        return 0;
    }
}
