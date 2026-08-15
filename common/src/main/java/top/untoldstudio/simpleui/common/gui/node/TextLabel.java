package top.untoldstudio.simpleui.common.gui.node;

import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.common.gui.Font;
import top.untoldstudio.simpleui.common.gui.FontManager;
import top.untoldstudio.simpleui.common.gui.GuiRender;
import top.untoldstudio.simpleui.common.gui.TextAlign;
import top.untoldstudio.simpleui.common.signal.SignalType;

public class TextLabel extends Frame {
    protected Font font = FontManager.getInstance().getDefaultFont();
    protected float scale = 1;
    protected ARGB textColor = ARGB.WHITE;
    protected String text;
    protected TextAlign align = TextAlign.CENTER;
    protected boolean hasShadow = true;
    protected boolean hasTextBackground = false;
    protected boolean isWrap = false;
    protected boolean canFrameBackgroundDisplay = false;

    @Override
    public void render(GuiRender render){
        if (canFrameBackgroundDisplay){
            super.render(render);
        }
        render.drawString(font, scale, realText, getRealPosition(), getRealSize(), textColor, align, isWrap, hasShadow, hasTextBackground);
    }

    public boolean canFrameBackgroundDisplay(){
        return canFrameBackgroundDisplay;
    }
    public ARGB getTextColor(){
        return textColor;
    }
    public String getText(){
        return text;
    }
    public TextAlign getAlign(){
        return align;
    }
    public float getScale(){
        return scale;
    }
    public boolean hasShadow(){
        return hasShadow;
    }
    public boolean hasBackground(){
        return hasTextBackground;
    }
    public boolean isWrap(){
        return isWrap;
    }
    public void setHasShadow(boolean hasShadow){
        if (this.hasShadow != hasShadow){
            this.hasShadow = hasShadow;
            sendSingle(SignalType.SET_HAS_SHADOW);
        }
    }
    public void setHasBackground(boolean hasBackground){
        if (this.hasTextBackground != hasBackground){
            this.hasTextBackground = hasBackground;
            sendSingle(SignalType.SET_HAS_BACKGROUND);
        }
    }
    public void setScale(float scale){
        if (this.scale != scale){
            this.scale = scale;
            sendSingle(SignalType.SET_TEXT_SCALE);
        }
    }
    public void setCanFrameBackgroundDisplay(boolean canFrameBackgroundDisplay){
        if (this.canFrameBackgroundDisplay != canFrameBackgroundDisplay){
            this.canFrameBackgroundDisplay = canFrameBackgroundDisplay;
            sendSingle(SignalType.SET_TEXT_LABEL_CAN_FRAME_BACKGROUND_DISPLAY);
        }
    }

    /**
     * @param isWrap 是否换行
     */
    public void setIsWrap(boolean isWrap){
        if (this.isWrap != isWrap){
            this.isWrap = isWrap;
            sendSingle(SignalType.SET_TEXT_IS_WRAP);
        }
    }
    public void setText(String text){
        if (!this.text.equals(text)){
            this.text = text;
            sendSingle(SignalType.SET_TEXT);
        }
    }
    public void setTextColor(ARGB color){
        if (this.color != color){
            this.textColor = color;
            sendSingle(SignalType.SET_TEXT_COLOR);
        }
    }

    public void setAlign(TextAlign align){
        if (this.align != align){
            this.align = align;
            sendSingle(SignalType.SET_TEXT_ALIGN);
        }
    }

    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
        this.text = text;
    }
    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size){
        super(position, size);
        this.text = text;
    }
    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
        this.text = text;
    }
    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
        this.text = text;
    }
}
