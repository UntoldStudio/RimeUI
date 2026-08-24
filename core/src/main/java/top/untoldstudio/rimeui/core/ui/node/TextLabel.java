package top.untoldstudio.rimeui.core.ui.node;

import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.font.Font;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public class TextLabel extends AbstractFrame<TextLabel> {
    private boolean canFrameBackgroundDisplay = false;
    private Font font;
    private String text;
    private int fontSize;

    @Override
    public void render(GuiRender render){
        if (canFrameBackgroundDisplay){
            super.renderFrameDefaultBackground(render);
        }
        render.drawString(text, font, realPosition, fontSize, color);
    }

    public TextLabel setFont(Font font){
        this.font = font;
        return this;
    }
    public TextLabel setText(String text){
        this.text = text;
        return this;
    }
    public TextLabel setFontSize(int fontSize){
        this.fontSize = fontSize;
        return this;
    }
    public TextLabel setCanFrameBackgroundDisplay(boolean canFrameBackgroundDisplay){
        this.canFrameBackgroundDisplay = canFrameBackgroundDisplay;
        return this;
    }

    public Font getFont(){
        return font;
    }
    public String getText(){
        return text;
    }
    public int getFontSize(){
        return fontSize;
    }
    public boolean canFrameBackgroundDisplay(){
        return canFrameBackgroundDisplay;
    }

    public TextLabel(ScaleOffset position, ScaleOffset size) {
        super(position, size);
    }
}
