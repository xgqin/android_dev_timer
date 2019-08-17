package com.glriverside.xgqin.ggtimer;

class TimerLog {
    private Long tick;
    private Integer number;
    private Long interval;

    private static Integer count = 0;

    public TimerLog() {
        this.tick = 0L;
        this.interval = 0L;
        this.number = ++count;
    }

    public TimerLog(Long tick, Long interval) {
        this.tick = tick;
        this.interval = interval;
        this.number = ++count;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return TimeUtils.intervalToString(tick);
    }

    public String intervalToString() {
        return TimeUtils.intervalToString(interval);
    }
}
