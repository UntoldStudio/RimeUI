package top.untoldstudio.simpleui.gui.node;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import top.untoldstudio.simpleui.Tools;
import top.untoldstudio.simpleui.core.ARGB;
import top.untoldstudio.simpleui.core.LayoutVector2;
import top.untoldstudio.simpleui.gui.GuiRender;
import top.untoldstudio.simpleui.gui.TextAlign;
import top.untoldstudio.simpleui.signal.SignalType;

/**
 * 注意：本类不会触发任何Component的点击与悬停事件
 */
public class TextLabel extends Frame {
    protected Font font = Minecraft.getInstance().font;
    protected Style customFont;
    protected float scale = 1;
    protected ARGB textColor = ARGB.WHITE;
    protected Component text;
    private Component realText;
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
    public Component getText(){
        return text;
    }
    public Component getRealText(){
        return realText;
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
    public void setText(Component text){
        if (this.text != text){
            this.text = text;
            this.realText = text.copy().withStyle(customFont);
            sendSingle(SignalType.SET_TEXT);
        }
    }
    public void setText(String text){
        setText(Component.literal(text));
    }
    public void setTextColor(ARGB color){
        if (this.color != color){
            this.textColor = color;
            sendSingle(SignalType.SET_TEXT_COLOR);
        }
    }

    /**
     * 请使用{@link Tools}的getCustomFontStyle来加载自定义字体
     * @param font 自定义字体
     */
    public void setFont(Style font){
        if (this.customFont != font){
            this.customFont = font;
            this.realText = text.copy().withStyle(customFont);
            sendSingle(SignalType.SET_TEXT_FONT);
        }
    }
    public void setAlign(TextAlign align){
        if (this.align != align){
            this.align = align;
            sendSingle(SignalType.SET_TEXT_ALIGN);
        }
    }

    public TextLabel(Component text, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
        this.text = text;
        this.realText = text;
    }
    public TextLabel(Component text, LayoutVector2 position, LayoutVector2 size){
        super(position, size);
        this.text = text;
        this.realText = text;
    }
    public TextLabel(Component text, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
        this.text = text;
        this.realText = text;
    }
    public TextLabel(Component text, LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
        this.text = text;
        this.realText = text;
    }
    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
        this.text = Component.literal(text);
        this.realText = this.text;
    }
    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size){
        super(position, size);
        this.text = Component.literal(text);
        this.realText = this.text;
    }
    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
        this.text = Component.literal(text);
        this.realText = this.text;
    }
    public TextLabel(String text, LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
        this.text = Component.literal(text);
        this.realText = this.text;
    }
}
