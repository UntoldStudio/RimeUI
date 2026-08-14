package top.untoldstudio.simpleui.neoforge.event;

public abstract class CanceledEvent {
    protected boolean isCanceled = false;

    public boolean isCanceled(){
        return isCanceled;
    }
    public void setCanceled(boolean isCanceled){
        this.isCanceled = isCanceled;
    }
    public void cancel(){
        setCanceled(true);
    }
}
