package top.untoldstudio.simpleui.common.core;

import java.util.Objects;

public record ARGB(int alpha, int red, int green, int blue) {
    public int getAlpha(){
        return alpha;
    }
    public int getRed(){
        return red;
    }
    public int getGreen(){
        return green;
    }
    public int getBlue(){
        return blue;
    }
    public int getIntColor(){
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
    public ARGB withAlpha(int alpha){
        return new ARGB(alpha, red, green, blue);
    }
    public ARGB withRed(int red){
        return new ARGB(alpha, red, green, blue);
    }
    public ARGB withGreen(int green){
        return new ARGB(alpha, red, green, blue);
    }
    public ARGB withBlue(int blue){
        return new ARGB(alpha, red, green, blue);
    }
    public static ARGB fromRGB(int r, int g, int b){
        return new ARGB(255, r, g, b);
    }
    public static ARGB fromInt(int color){
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8)  & 0xFF;
        int blue = color & 0xFF;
        return new ARGB(alpha, red, green, blue);
    }
    @Override
    public boolean equals(Object other){
        if (other instanceof ARGB(int alpha1, int red1, int green1, int blue1)){
            return alpha1 == alpha && red1 == red && green1 == green && blue1 == blue;
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(alpha, red, green, blue);
    }

    public static final ARGB WHITE = new ARGB(255, 255, 255, 255);
    public static final ARGB WHITE_25 = WHITE.withAlpha(64);
    public static final ARGB WHITE_50 = WHITE.withAlpha(128);
    public static final ARGB WHITE_75 = WHITE.withAlpha(191);

    public static final ARGB RED = new ARGB(255, 255, 0, 0);
    public static final ARGB RED_25 = RED.withAlpha(64);
    public static final ARGB RED_50 = RED.withAlpha(128);
    public static final ARGB RED_75 = RED.withAlpha(191);

    public static final ARGB GREEN = new ARGB(255, 0, 255, 0);
    public static final ARGB GREEN_25 = GREEN.withAlpha(64);
    public static final ARGB GREEN_50 = GREEN.withAlpha(128);
    public static final ARGB GREEN_75 = GREEN.withAlpha(191);

    public static final ARGB BLUE = new ARGB(255, 0, 0, 255);
    public static final ARGB BLUE_25 = BLUE.withAlpha(64);
    public static final ARGB BLUE_50 = BLUE.withAlpha(128);
    public static final ARGB BLUE_75 = BLUE.withAlpha(191);

    public static final ARGB BLACK = new ARGB(255, 0, 0, 0);
    public static final ARGB BLACK_25 = BLACK.withAlpha(64);
    public static final ARGB BLACK_50 = BLACK.withAlpha(128);
    public static final ARGB BLACK_75 = BLACK.withAlpha(191);

    public static final ARGB SNOW = new ARGB(255, 255, 250, 250);
    public static final ARGB SNOW_25 = SNOW.withAlpha(64);
    public static final ARGB SNOW_50 = SNOW.withAlpha(128);
    public static final ARGB SNOW_75 = SNOW.withAlpha(191);

    public static final ARGB GHOST_WHITE = new ARGB(255, 248, 248, 255);
    public static final ARGB GHOST_WHITE_25 = GHOST_WHITE.withAlpha(64);
    public static final ARGB GHOST_WHITE_50 = GHOST_WHITE.withAlpha(128);
    public static final ARGB GHOST_WHITE_75 = GHOST_WHITE.withAlpha(191);

    public static final ARGB WHITE_SMOKE = new ARGB(255, 245, 245, 245);
    public static final ARGB WHITE_SMOKE_25 = WHITE_SMOKE.withAlpha(64);
    public static final ARGB WHITE_SMOKE_50 = WHITE_SMOKE.withAlpha(128);
    public static final ARGB WHITE_SMOKE_75 = WHITE_SMOKE.withAlpha(191);

    public static final ARGB GAINSBORO = new ARGB(255, 220, 220, 220);
    public static final ARGB GAINSBORO_25 = GAINSBORO.withAlpha(64);
    public static final ARGB GAINSBORO_50 = GAINSBORO.withAlpha(128);
    public static final ARGB GAINSBORO_75 = GAINSBORO.withAlpha(191);

    public static final ARGB FLORAL_WHITE = new ARGB(255, 255, 250, 240);
    public static final ARGB FLORAL_WHITE_25 = FLORAL_WHITE.withAlpha(64);
    public static final ARGB FLORAL_WHITE_50 = FLORAL_WHITE.withAlpha(128);
    public static final ARGB FLORAL_WHITE_75 = FLORAL_WHITE.withAlpha(191);

    public static final ARGB OLD_LACE = new ARGB(255, 253, 245, 230);
    public static final ARGB OLD_LACE_25 = OLD_LACE.withAlpha(64);
    public static final ARGB OLD_LACE_50 = OLD_LACE.withAlpha(128);
    public static final ARGB OLD_LACE_75 = OLD_LACE.withAlpha(191);

    public static final ARGB LINEN = new ARGB(255, 250, 240, 230);
    public static final ARGB LINEN_25 = LINEN.withAlpha(64);
    public static final ARGB LINEN_50 = LINEN.withAlpha(128);
    public static final ARGB LINEN_75 = LINEN.withAlpha(191);

    public static final ARGB ANTIQUE_WHITE = new ARGB(255, 250, 235, 215);
    public static final ARGB ANTIQUE_WHITE_25 = ANTIQUE_WHITE.withAlpha(64);
    public static final ARGB ANTIQUE_WHITE_50 = ANTIQUE_WHITE.withAlpha(128);
    public static final ARGB ANTIQUE_WHITE_75 = ANTIQUE_WHITE.withAlpha(191);

    public static final ARGB PAPAYA_WHIP = new ARGB(255, 255, 239, 213);
    public static final ARGB PAPAYA_WHIP_25 = PAPAYA_WHIP.withAlpha(64);
    public static final ARGB PAPAYA_WHIP_50 = PAPAYA_WHIP.withAlpha(128);
    public static final ARGB PAPAYA_WHIP_75 = PAPAYA_WHIP.withAlpha(191);

    public static final ARGB BLANCHED_ALMOND = new ARGB(255, 255, 235, 205);
    public static final ARGB BLANCHED_ALMOND_25 = BLANCHED_ALMOND.withAlpha(64);
    public static final ARGB BLANCHED_ALMOND_50 = BLANCHED_ALMOND.withAlpha(128);
    public static final ARGB BLANCHED_ALMOND_75 = BLANCHED_ALMOND.withAlpha(191);

    public static final ARGB BISQUE = new ARGB(255, 255, 228, 196);
    public static final ARGB BISQUE_25 = BISQUE.withAlpha(64);
    public static final ARGB BISQUE_50 = BISQUE.withAlpha(128);
    public static final ARGB BISQUE_75 = BISQUE.withAlpha(191);

    public static final ARGB PEACH_PUFF = new ARGB(255, 255, 218, 185);
    public static final ARGB PEACH_PUFF_25 = PEACH_PUFF.withAlpha(64);
    public static final ARGB PEACH_PUFF_50 = PEACH_PUFF.withAlpha(128);
    public static final ARGB PEACH_PUFF_75 = PEACH_PUFF.withAlpha(191);

    public static final ARGB NAVAJO_WHITE = new ARGB(255, 255, 222, 173);
    public static final ARGB NAVAJO_WHITE_25 = NAVAJO_WHITE.withAlpha(64);
    public static final ARGB NAVAJO_WHITE_50 = NAVAJO_WHITE.withAlpha(128);
    public static final ARGB NAVAJO_WHITE_75 = NAVAJO_WHITE.withAlpha(191);

    public static final ARGB MOCCASIN = new ARGB(255, 255, 228, 181);
    public static final ARGB MOCCASIN_25 = MOCCASIN.withAlpha(64);
    public static final ARGB MOCCASIN_50 = MOCCASIN.withAlpha(128);
    public static final ARGB MOCCASIN_75 = MOCCASIN.withAlpha(191);

    public static final ARGB CORN_SILK = new ARGB(255, 255, 248, 220);
    public static final ARGB CORN_SILK_25 = CORN_SILK.withAlpha(64);
    public static final ARGB CORN_SILK_50 = CORN_SILK.withAlpha(128);
    public static final ARGB CORN_SILK_75 = CORN_SILK.withAlpha(191);

    public static final ARGB IVORY = new ARGB(255, 255, 255, 240);
    public static final ARGB IVORY_25 = IVORY.withAlpha(64);
    public static final ARGB IVORY_50 = IVORY.withAlpha(128);
    public static final ARGB IVORY_75 = IVORY.withAlpha(191);

    public static final ARGB LEMON_CHIFFON = new ARGB(255, 255, 250, 205);
    public static final ARGB LEMON_CHIFFON_25 = LEMON_CHIFFON.withAlpha(64);
    public static final ARGB LEMON_CHIFFON_50 = LEMON_CHIFFON.withAlpha(128);
    public static final ARGB LEMON_CHIFFON_75 = LEMON_CHIFFON.withAlpha(191);

    public static final ARGB SEA_SHELL = new ARGB(255, 255, 245, 238);
    public static final ARGB SEA_SHELL_25 = SEA_SHELL.withAlpha(64);
    public static final ARGB SEA_SHELL_50 = SEA_SHELL.withAlpha(128);
    public static final ARGB SEA_SHELL_75 = SEA_SHELL.withAlpha(191);

    public static final ARGB HONEYDEW = new ARGB(255, 240, 255, 240);
    public static final ARGB HONEYDEW_25 = HONEYDEW.withAlpha(64);
    public static final ARGB HONEYDEW_50 = HONEYDEW.withAlpha(128);
    public static final ARGB HONEYDEW_75 = HONEYDEW.withAlpha(191);

    public static final ARGB MINT_CREAM = new ARGB(255, 245, 255, 250);
    public static final ARGB MINT_CREAM_25 = MINT_CREAM.withAlpha(64);
    public static final ARGB MINT_CREAM_50 = MINT_CREAM.withAlpha(128);
    public static final ARGB MINT_CREAM_75 = MINT_CREAM.withAlpha(191);

    public static final ARGB AZURE = new ARGB(255, 240, 255, 255);
    public static final ARGB AZURE_25 = AZURE.withAlpha(64);
    public static final ARGB AZURE_50 = AZURE.withAlpha(128);
    public static final ARGB AZURE_75 = AZURE.withAlpha(191);

    public static final ARGB ALICE_BLUE = new ARGB(255, 240, 248, 255);
    public static final ARGB ALICE_BLUE_25 = ALICE_BLUE.withAlpha(64);
    public static final ARGB ALICE_BLUE_50 = ALICE_BLUE.withAlpha(128);
    public static final ARGB ALICE_BLUE_75 = ALICE_BLUE.withAlpha(191);

    public static final ARGB LAVENDER = new ARGB(255, 230, 230, 250);
    public static final ARGB LAVENDER_25 = LAVENDER.withAlpha(64);
    public static final ARGB LAVENDER_50 = LAVENDER.withAlpha(128);
    public static final ARGB LAVENDER_75 = LAVENDER.withAlpha(191);

    public static final ARGB LAVENDER_BLUSH = new ARGB(255, 255, 240, 245);
    public static final ARGB LAVENDER_BLUSH_25 = LAVENDER_BLUSH.withAlpha(64);
    public static final ARGB LAVENDER_BLUSH_50 = LAVENDER_BLUSH.withAlpha(128);
    public static final ARGB LAVENDER_BLUSH_75 = LAVENDER_BLUSH.withAlpha(191);

    public static final ARGB MISTY_ROSE = new ARGB(255, 255, 228, 225);
    public static final ARGB MISTY_ROSE_25 = MISTY_ROSE.withAlpha(64);
    public static final ARGB MISTY_ROSE_50 = MISTY_ROSE.withAlpha(128);
    public static final ARGB MISTY_ROSE_75 = MISTY_ROSE.withAlpha(191);

    public static final ARGB LIGHT_CYAN = new ARGB(255, 224, 255, 255);
    public static final ARGB LIGHT_CYAN_25 = LIGHT_CYAN.withAlpha(64);
    public static final ARGB LIGHT_CYAN_50 = LIGHT_CYAN.withAlpha(128);
    public static final ARGB LIGHT_CYAN_75 = LIGHT_CYAN.withAlpha(191);

    public static final ARGB CYAN = new ARGB(255, 0, 255, 255);
    public static final ARGB CYAN_25 = CYAN.withAlpha(64);
    public static final ARGB CYAN_50 = CYAN.withAlpha(128);
    public static final ARGB CYAN_75 = CYAN.withAlpha(191);

    public static final ARGB AQUA = new ARGB(255, 0, 255, 255);
    public static final ARGB AQUA_25 = AQUA.withAlpha(64);
    public static final ARGB AQUA_50 = AQUA.withAlpha(128);
    public static final ARGB AQUA_75 = AQUA.withAlpha(191);

    public static final ARGB AQUAMARINE = new ARGB(255, 127, 255, 212);
    public static final ARGB AQUAMARINE_25 = AQUAMARINE.withAlpha(64);
    public static final ARGB AQUAMARINE_50 = AQUAMARINE.withAlpha(128);
    public static final ARGB AQUAMARINE_75 = AQUAMARINE.withAlpha(191);

    public static final ARGB MEDIUM_AQUAMARINE = new ARGB(255, 102, 205, 170);
    public static final ARGB MEDIUM_AQUAMARINE_25 = MEDIUM_AQUAMARINE.withAlpha(64);
    public static final ARGB MEDIUM_AQUAMARINE_50 = MEDIUM_AQUAMARINE.withAlpha(128);
    public static final ARGB MEDIUM_AQUAMARINE_75 = MEDIUM_AQUAMARINE.withAlpha(191);

    public static final ARGB PALE_TURQUOISE = new ARGB(255, 175, 238, 238);
    public static final ARGB PALE_TURQUOISE_25 = PALE_TURQUOISE.withAlpha(64);
    public static final ARGB PALE_TURQUOISE_50 = PALE_TURQUOISE.withAlpha(128);
    public static final ARGB PALE_TURQUOISE_75 = PALE_TURQUOISE.withAlpha(191);

    public static final ARGB TURQUOISE = new ARGB(255, 64, 224, 208);
    public static final ARGB TURQUOISE_25 = TURQUOISE.withAlpha(64);
    public static final ARGB TURQUOISE_50 = TURQUOISE.withAlpha(128);
    public static final ARGB TURQUOISE_75 = TURQUOISE.withAlpha(191);

    public static final ARGB MEDIUM_TURQUOISE = new ARGB(255, 72, 209, 204);
    public static final ARGB MEDIUM_TURQUOISE_25 = MEDIUM_TURQUOISE.withAlpha(64);
    public static final ARGB MEDIUM_TURQUOISE_50 = MEDIUM_TURQUOISE.withAlpha(128);
    public static final ARGB MEDIUM_TURQUOISE_75 = MEDIUM_TURQUOISE.withAlpha(191);

    public static final ARGB DARK_TURQUOISE = new ARGB(255, 0, 206, 209);
    public static final ARGB DARK_TURQUOISE_25 = DARK_TURQUOISE.withAlpha(64);
    public static final ARGB DARK_TURQUOISE_50 = DARK_TURQUOISE.withAlpha(128);
    public static final ARGB DARK_TURQUOISE_75 = DARK_TURQUOISE.withAlpha(191);

    public static final ARGB CADET_BLUE = new ARGB(255, 95, 158, 160);
    public static final ARGB CADET_BLUE_25 = CADET_BLUE.withAlpha(64);
    public static final ARGB CADET_BLUE_50 = CADET_BLUE.withAlpha(128);
    public static final ARGB CADET_BLUE_75 = CADET_BLUE.withAlpha(191);

    public static final ARGB POWDER_BLUE = new ARGB(255, 176, 224, 230);
    public static final ARGB POWDER_BLUE_25 = POWDER_BLUE.withAlpha(64);
    public static final ARGB POWDER_BLUE_50 = POWDER_BLUE.withAlpha(128);
    public static final ARGB POWDER_BLUE_75 = POWDER_BLUE.withAlpha(191);

    public static final ARGB LIGHT_BLUE = new ARGB(255, 173, 216, 230);
    public static final ARGB LIGHT_BLUE_25 = LIGHT_BLUE.withAlpha(64);
    public static final ARGB LIGHT_BLUE_50 = LIGHT_BLUE.withAlpha(128);
    public static final ARGB LIGHT_BLUE_75 = LIGHT_BLUE.withAlpha(191);

    public static final ARGB SKY_BLUE = new ARGB(255, 135, 206, 235);
    public static final ARGB SKY_BLUE_25 = SKY_BLUE.withAlpha(64);
    public static final ARGB SKY_BLUE_50 = SKY_BLUE.withAlpha(128);
    public static final ARGB SKY_BLUE_75 = SKY_BLUE.withAlpha(191);

    public static final ARGB LIGHT_SKY_BLUE = new ARGB(255, 135, 206, 250);
    public static final ARGB LIGHT_SKY_BLUE_25 = LIGHT_SKY_BLUE.withAlpha(64);
    public static final ARGB LIGHT_SKY_BLUE_50 = LIGHT_SKY_BLUE.withAlpha(128);
    public static final ARGB LIGHT_SKY_BLUE_75 = LIGHT_SKY_BLUE.withAlpha(191);

    public static final ARGB DEEP_SKY_BLUE = new ARGB(255, 0, 191, 255);
    public static final ARGB DEEP_SKY_BLUE_25 = DEEP_SKY_BLUE.withAlpha(64);
    public static final ARGB DEEP_SKY_BLUE_50 = DEEP_SKY_BLUE.withAlpha(128);
    public static final ARGB DEEP_SKY_BLUE_75 = DEEP_SKY_BLUE.withAlpha(191);

    public static final ARGB DODGER_BLUE = new ARGB(255, 30, 144, 255);
    public static final ARGB DODGER_BLUE_25 = DODGER_BLUE.withAlpha(64);
    public static final ARGB DODGER_BLUE_50 = DODGER_BLUE.withAlpha(128);
    public static final ARGB DODGER_BLUE_75 = DODGER_BLUE.withAlpha(191);

    public static final ARGB CORN_FLOWER_BLUE = new ARGB(255, 100, 149, 237);
    public static final ARGB CORN_FLOWER_BLUE_25 = CORN_FLOWER_BLUE.withAlpha(64);
    public static final ARGB CORN_FLOWER_BLUE_50 = CORN_FLOWER_BLUE.withAlpha(128);
    public static final ARGB CORN_FLOWER_BLUE_75 = CORN_FLOWER_BLUE.withAlpha(191);

    public static final ARGB STEEL_BLUE = new ARGB(255, 70, 130, 180);
    public static final ARGB STEEL_BLUE_25 = STEEL_BLUE.withAlpha(64);
    public static final ARGB STEEL_BLUE_50 = STEEL_BLUE.withAlpha(128);
    public static final ARGB STEEL_BLUE_75 = STEEL_BLUE.withAlpha(191);

    public static final ARGB LIGHT_STEEL_BLUE = new ARGB(255, 176, 196, 222);
    public static final ARGB LIGHT_STEEL_BLUE_25 = LIGHT_STEEL_BLUE.withAlpha(64);
    public static final ARGB LIGHT_STEEL_BLUE_50 = LIGHT_STEEL_BLUE.withAlpha(128);
    public static final ARGB LIGHT_STEEL_BLUE_75 = LIGHT_STEEL_BLUE.withAlpha(191);

    public static final ARGB ROYAL_BLUE = new ARGB(255, 65, 105, 225);
    public static final ARGB ROYAL_BLUE_25 = ROYAL_BLUE.withAlpha(64);
    public static final ARGB ROYAL_BLUE_50 = ROYAL_BLUE.withAlpha(128);
    public static final ARGB ROYAL_BLUE_75 = ROYAL_BLUE.withAlpha(191);

    public static final ARGB MEDIUM_BLUE = new ARGB(255, 0, 0, 205);
    public static final ARGB MEDIUM_BLUE_25 = MEDIUM_BLUE.withAlpha(64);
    public static final ARGB MEDIUM_BLUE_50 = MEDIUM_BLUE.withAlpha(128);
    public static final ARGB MEDIUM_BLUE_75 = MEDIUM_BLUE.withAlpha(191);

    public static final ARGB DARK_BLUE = new ARGB(255, 0, 0, 139);
    public static final ARGB DARK_BLUE_25 = DARK_BLUE.withAlpha(64);
    public static final ARGB DARK_BLUE_50 = DARK_BLUE.withAlpha(128);
    public static final ARGB DARK_BLUE_75 = DARK_BLUE.withAlpha(191);

    public static final ARGB NAVY = new ARGB(255, 0, 0, 128);
    public static final ARGB NAVY_25 = NAVY.withAlpha(64);
    public static final ARGB NAVY_50 = NAVY.withAlpha(128);
    public static final ARGB NAVY_75 = NAVY.withAlpha(191);

    public static final ARGB MIDNIGHT_BLUE = new ARGB(255, 25, 25, 112);
    public static final ARGB MIDNIGHT_BLUE_25 = MIDNIGHT_BLUE.withAlpha(64);
    public static final ARGB MIDNIGHT_BLUE_50 = MIDNIGHT_BLUE.withAlpha(128);
    public static final ARGB MIDNIGHT_BLUE_75 = MIDNIGHT_BLUE.withAlpha(191);

    public static final ARGB BLUE_VIOLET = new ARGB(255, 138, 43, 226);
    public static final ARGB BLUE_VIOLET_25 = BLUE_VIOLET.withAlpha(64);
    public static final ARGB BLUE_VIOLET_50 = BLUE_VIOLET.withAlpha(128);
    public static final ARGB BLUE_VIOLET_75 = BLUE_VIOLET.withAlpha(191);

    public static final ARGB INDIGO = new ARGB(255, 75, 0, 130);
    public static final ARGB INDIGO_25 = INDIGO.withAlpha(64);
    public static final ARGB INDIGO_50 = INDIGO.withAlpha(128);
    public static final ARGB INDIGO_75 = INDIGO.withAlpha(191);

    public static final ARGB SLATE_BLUE = new ARGB(255, 106, 90, 205);
    public static final ARGB SLATE_BLUE_25 = SLATE_BLUE.withAlpha(64);
    public static final ARGB SLATE_BLUE_50 = SLATE_BLUE.withAlpha(128);
    public static final ARGB SLATE_BLUE_75 = SLATE_BLUE.withAlpha(191);

    public static final ARGB MEDIUM_SLATE_BLUE = new ARGB(255, 123, 104, 238);
    public static final ARGB MEDIUM_SLATE_BLUE_25 = MEDIUM_SLATE_BLUE.withAlpha(64);
    public static final ARGB MEDIUM_SLATE_BLUE_50 = MEDIUM_SLATE_BLUE.withAlpha(128);
    public static final ARGB MEDIUM_SLATE_BLUE_75 = MEDIUM_SLATE_BLUE.withAlpha(191);

    public static final ARGB DARK_SLATE_BLUE = new ARGB(255, 72, 61, 139);
    public static final ARGB DARK_SLATE_BLUE_25 = DARK_SLATE_BLUE.withAlpha(64);
    public static final ARGB DARK_SLATE_BLUE_50 = DARK_SLATE_BLUE.withAlpha(128);
    public static final ARGB DARK_SLATE_BLUE_75 = DARK_SLATE_BLUE.withAlpha(191);

    public static final ARGB MEDIUM_PURPLE = new ARGB(255, 147, 112, 219);
    public static final ARGB MEDIUM_PURPLE_25 = MEDIUM_PURPLE.withAlpha(64);
    public static final ARGB MEDIUM_PURPLE_50 = MEDIUM_PURPLE.withAlpha(128);
    public static final ARGB MEDIUM_PURPLE_75 = MEDIUM_PURPLE.withAlpha(191);

    public static final ARGB PURPLE = new ARGB(255, 128, 0, 128);
    public static final ARGB PURPLE_25 = PURPLE.withAlpha(64);
    public static final ARGB PURPLE_50 = PURPLE.withAlpha(128);
    public static final ARGB PURPLE_75 = PURPLE.withAlpha(191);

    public static final ARGB DARK_ORCHID = new ARGB(255, 153, 50, 204);
    public static final ARGB DARK_ORCHID_25 = DARK_ORCHID.withAlpha(64);
    public static final ARGB DARK_ORCHID_50 = DARK_ORCHID.withAlpha(128);
    public static final ARGB DARK_ORCHID_75 = DARK_ORCHID.withAlpha(191);

    public static final ARGB DARK_VIOLET = new ARGB(255, 148, 0, 211);
    public static final ARGB DARK_VIOLET_25 = DARK_VIOLET.withAlpha(64);
    public static final ARGB DARK_VIOLET_50 = DARK_VIOLET.withAlpha(128);
    public static final ARGB DARK_VIOLET_75 = DARK_VIOLET.withAlpha(191);

    public static final ARGB MEDIUM_ORCHID = new ARGB(255, 186, 85, 211);
    public static final ARGB MEDIUM_ORCHID_25 = MEDIUM_ORCHID.withAlpha(64);
    public static final ARGB MEDIUM_ORCHID_50 = MEDIUM_ORCHID.withAlpha(128);
    public static final ARGB MEDIUM_ORCHID_75 = MEDIUM_ORCHID.withAlpha(191);

    public static final ARGB ORCHID = new ARGB(255, 218, 112, 214);
    public static final ARGB ORCHID_25 = ORCHID.withAlpha(64);
    public static final ARGB ORCHID_50 = ORCHID.withAlpha(128);
    public static final ARGB ORCHID_75 = ORCHID.withAlpha(191);

    public static final ARGB VIOLET = new ARGB(255, 238, 130, 238);
    public static final ARGB VIOLET_25 = VIOLET.withAlpha(64);
    public static final ARGB VIOLET_50 = VIOLET.withAlpha(128);
    public static final ARGB VIOLET_75 = VIOLET.withAlpha(191);

    public static final ARGB PLUM = new ARGB(255, 221, 160, 221);
    public static final ARGB PLUM_25 = PLUM.withAlpha(64);
    public static final ARGB PLUM_50 = PLUM.withAlpha(128);
    public static final ARGB PLUM_75 = PLUM.withAlpha(191);

    public static final ARGB THISTLE = new ARGB(255, 216, 191, 216);
    public static final ARGB THISTLE_25 = THISTLE.withAlpha(64);
    public static final ARGB THISTLE_50 = THISTLE.withAlpha(128);
    public static final ARGB THISTLE_75 = THISTLE.withAlpha(191);

    public static final ARGB MAGENTA = new ARGB(255, 255, 0, 255);
    public static final ARGB MAGENTA_25 = MAGENTA.withAlpha(64);
    public static final ARGB MAGENTA_50 = MAGENTA.withAlpha(128);
    public static final ARGB MAGENTA_75 = MAGENTA.withAlpha(191);

    public static final ARGB FUCHSIA = new ARGB(255, 255, 0, 255);
    public static final ARGB FUCHSIA_25 = FUCHSIA.withAlpha(64);
    public static final ARGB FUCHSIA_50 = FUCHSIA.withAlpha(128);
    public static final ARGB FUCHSIA_75 = FUCHSIA.withAlpha(191);

    public static final ARGB DARK_MAGENTA = new ARGB(255, 139, 0, 139);
    public static final ARGB DARK_MAGENTA_25 = DARK_MAGENTA.withAlpha(64);
    public static final ARGB DARK_MAGENTA_50 = DARK_MAGENTA.withAlpha(128);
    public static final ARGB DARK_MAGENTA_75 = DARK_MAGENTA.withAlpha(191);

    public static final ARGB DEEP_PINK = new ARGB(255, 255, 20, 147);
    public static final ARGB DEEP_PINK_25 = DEEP_PINK.withAlpha(64);
    public static final ARGB DEEP_PINK_50 = DEEP_PINK.withAlpha(128);
    public static final ARGB DEEP_PINK_75 = DEEP_PINK.withAlpha(191);

    public static final ARGB HOT_PINK = new ARGB(255, 255, 105, 180);
    public static final ARGB HOT_PINK_25 = HOT_PINK.withAlpha(64);
    public static final ARGB HOT_PINK_50 = HOT_PINK.withAlpha(128);
    public static final ARGB HOT_PINK_75 = HOT_PINK.withAlpha(191);

    public static final ARGB PALE_VIOLET_RED = new ARGB(255, 219, 112, 147);
    public static final ARGB PALE_VIOLET_RED_25 = PALE_VIOLET_RED.withAlpha(64);
    public static final ARGB PALE_VIOLET_RED_50 = PALE_VIOLET_RED.withAlpha(128);
    public static final ARGB PALE_VIOLET_RED_75 = PALE_VIOLET_RED.withAlpha(191);

    public static final ARGB MEDIUM_VIOLET_RED = new ARGB(255, 199, 21, 133);
    public static final ARGB MEDIUM_VIOLET_RED_25 = MEDIUM_VIOLET_RED.withAlpha(64);
    public static final ARGB MEDIUM_VIOLET_RED_50 = MEDIUM_VIOLET_RED.withAlpha(128);
    public static final ARGB MEDIUM_VIOLET_RED_75 = MEDIUM_VIOLET_RED.withAlpha(191);

    public static final ARGB PINK = new ARGB(255, 255, 192, 203);
    public static final ARGB PINK_25 = PINK.withAlpha(64);
    public static final ARGB PINK_50 = PINK.withAlpha(128);
    public static final ARGB PINK_75 = PINK.withAlpha(191);

    public static final ARGB LIGHT_PINK = new ARGB(255, 255, 182, 193);
    public static final ARGB LIGHT_PINK_25 = LIGHT_PINK.withAlpha(64);
    public static final ARGB LIGHT_PINK_50 = LIGHT_PINK.withAlpha(128);
    public static final ARGB LIGHT_PINK_75 = LIGHT_PINK.withAlpha(191);

    public static final ARGB CRIMSON = new ARGB(255, 220, 20, 60);
    public static final ARGB CRIMSON_25 = CRIMSON.withAlpha(64);
    public static final ARGB CRIMSON_50 = CRIMSON.withAlpha(128);
    public static final ARGB CRIMSON_75 = CRIMSON.withAlpha(191);

    public static final ARGB DARK_RED = new ARGB(255, 139, 0, 0);
    public static final ARGB DARK_RED_25 = DARK_RED.withAlpha(64);
    public static final ARGB DARK_RED_50 = DARK_RED.withAlpha(128);
    public static final ARGB DARK_RED_75 = DARK_RED.withAlpha(191);

    public static final ARGB FIRE_BRICK = new ARGB(255, 178, 34, 34);
    public static final ARGB FIRE_BRICK_25 = FIRE_BRICK.withAlpha(64);
    public static final ARGB FIRE_BRICK_50 = FIRE_BRICK.withAlpha(128);
    public static final ARGB FIRE_BRICK_75 = FIRE_BRICK.withAlpha(191);

    public static final ARGB INDIAN_RED = new ARGB(255, 205, 92, 92);
    public static final ARGB INDIAN_RED_25 = INDIAN_RED.withAlpha(64);
    public static final ARGB INDIAN_RED_50 = INDIAN_RED.withAlpha(128);
    public static final ARGB INDIAN_RED_75 = INDIAN_RED.withAlpha(191);

    public static final ARGB LIGHT_CORAL = new ARGB(255, 240, 128, 128);
    public static final ARGB LIGHT_CORAL_25 = LIGHT_CORAL.withAlpha(64);
    public static final ARGB LIGHT_CORAL_50 = LIGHT_CORAL.withAlpha(128);
    public static final ARGB LIGHT_CORAL_75 = LIGHT_CORAL.withAlpha(191);

    public static final ARGB SALMON = new ARGB(255, 250, 128, 114);
    public static final ARGB SALMON_25 = SALMON.withAlpha(64);
    public static final ARGB SALMON_50 = SALMON.withAlpha(128);
    public static final ARGB SALMON_75 = SALMON.withAlpha(191);

    public static final ARGB DARK_SALMON = new ARGB(255, 233, 150, 122);
    public static final ARGB DARK_SALMON_25 = DARK_SALMON.withAlpha(64);
    public static final ARGB DARK_SALMON_50 = DARK_SALMON.withAlpha(128);
    public static final ARGB DARK_SALMON_75 = DARK_SALMON.withAlpha(191);

    public static final ARGB LIGHT_SALMON = new ARGB(255, 255, 160, 122);
    public static final ARGB LIGHT_SALMON_25 = LIGHT_SALMON.withAlpha(64);
    public static final ARGB LIGHT_SALMON_50 = LIGHT_SALMON.withAlpha(128);
    public static final ARGB LIGHT_SALMON_75 = LIGHT_SALMON.withAlpha(191);

    public static final ARGB TOMATO = new ARGB(255, 255, 99, 71);
    public static final ARGB TOMATO_25 = TOMATO.withAlpha(64);
    public static final ARGB TOMATO_50 = TOMATO.withAlpha(128);
    public static final ARGB TOMATO_75 = TOMATO.withAlpha(191);

    public static final ARGB ORANGE_RED = new ARGB(255, 255, 69, 0);
    public static final ARGB ORANGE_RED_25 = ORANGE_RED.withAlpha(64);
    public static final ARGB ORANGE_RED_50 = ORANGE_RED.withAlpha(128);
    public static final ARGB ORANGE_RED_75 = ORANGE_RED.withAlpha(191);

    public static final ARGB DARK_ORANGE = new ARGB(255, 255, 140, 0);
    public static final ARGB DARK_ORANGE_25 = DARK_ORANGE.withAlpha(64);
    public static final ARGB DARK_ORANGE_50 = DARK_ORANGE.withAlpha(128);
    public static final ARGB DARK_ORANGE_75 = DARK_ORANGE.withAlpha(191);

    public static final ARGB ORANGE = new ARGB(255, 255, 165, 0);
    public static final ARGB ORANGE_25 = ORANGE.withAlpha(64);
    public static final ARGB ORANGE_50 = ORANGE.withAlpha(128);
    public static final ARGB ORANGE_75 = ORANGE.withAlpha(191);

    public static final ARGB CORAL = new ARGB(255, 255, 127, 80);
    public static final ARGB CORAL_25 = CORAL.withAlpha(64);
    public static final ARGB CORAL_50 = CORAL.withAlpha(128);
    public static final ARGB CORAL_75 = CORAL.withAlpha(191);

    public static final ARGB GOLD = new ARGB(255, 255, 215, 0);
    public static final ARGB GOLD_25 = GOLD.withAlpha(64);
    public static final ARGB GOLD_50 = GOLD.withAlpha(128);
    public static final ARGB GOLD_75 = GOLD.withAlpha(191);

    public static final ARGB YELLOW = new ARGB(255, 255, 255, 0);
    public static final ARGB YELLOW_25 = YELLOW.withAlpha(64);
    public static final ARGB YELLOW_50 = YELLOW.withAlpha(128);
    public static final ARGB YELLOW_75 = YELLOW.withAlpha(191);

    public static final ARGB LIGHT_YELLOW = new ARGB(255, 255, 255, 224);
    public static final ARGB LIGHT_YELLOW_25 = LIGHT_YELLOW.withAlpha(64);
    public static final ARGB LIGHT_YELLOW_50 = LIGHT_YELLOW.withAlpha(128);
    public static final ARGB LIGHT_YELLOW_75 = LIGHT_YELLOW.withAlpha(191);

    public static final ARGB LIGHT_GOLDENROD_YELLOW = new ARGB(255, 250, 250, 210);
    public static final ARGB LIGHT_GOLDENROD_YELLOW_25 = LIGHT_GOLDENROD_YELLOW.withAlpha(64);
    public static final ARGB LIGHT_GOLDENROD_YELLOW_50 = LIGHT_GOLDENROD_YELLOW.withAlpha(128);
    public static final ARGB LIGHT_GOLDENROD_YELLOW_75 = LIGHT_GOLDENROD_YELLOW.withAlpha(191);

    public static final ARGB PALE_GOLDENROD = new ARGB(255, 238, 232, 170);
    public static final ARGB PALE_GOLDENROD_25 = PALE_GOLDENROD.withAlpha(64);
    public static final ARGB PALE_GOLDENROD_50 = PALE_GOLDENROD.withAlpha(128);
    public static final ARGB PALE_GOLDENROD_75 = PALE_GOLDENROD.withAlpha(191);

    public static final ARGB KHAKI = new ARGB(255, 240, 230, 140);
    public static final ARGB KHAKI_25 = KHAKI.withAlpha(64);
    public static final ARGB KHAKI_50 = KHAKI.withAlpha(128);
    public static final ARGB KHAKI_75 = KHAKI.withAlpha(191);

    public static final ARGB DARK_KHAKI = new ARGB(255, 189, 183, 107);
    public static final ARGB DARK_KHAKI_25 = DARK_KHAKI.withAlpha(64);
    public static final ARGB DARK_KHAKI_50 = DARK_KHAKI.withAlpha(128);
    public static final ARGB DARK_KHAKI_75 = DARK_KHAKI.withAlpha(191);

    public static final ARGB GREEN_YELLOW = new ARGB(255, 173, 255, 47);
    public static final ARGB GREEN_YELLOW_25 = GREEN_YELLOW.withAlpha(64);
    public static final ARGB GREEN_YELLOW_50 = GREEN_YELLOW.withAlpha(128);
    public static final ARGB GREEN_YELLOW_75 = GREEN_YELLOW.withAlpha(191);

    public static final ARGB CHARTREUSE = new ARGB(255, 127, 255, 0);
    public static final ARGB CHARTREUSE_25 = CHARTREUSE.withAlpha(64);
    public static final ARGB CHARTREUSE_50 = CHARTREUSE.withAlpha(128);
    public static final ARGB CHARTREUSE_75 = CHARTREUSE.withAlpha(191);

    public static final ARGB LAWN_GREEN = new ARGB(255, 124, 252, 0);
    public static final ARGB LAWN_GREEN_25 = LAWN_GREEN.withAlpha(64);
    public static final ARGB LAWN_GREEN_50 = LAWN_GREEN.withAlpha(128);
    public static final ARGB LAWN_GREEN_75 = LAWN_GREEN.withAlpha(191);

    public static final ARGB LIME = new ARGB(255, 0, 255, 0);
    public static final ARGB LIME_25 = LIME.withAlpha(64);
    public static final ARGB LIME_50 = LIME.withAlpha(128);
    public static final ARGB LIME_75 = LIME.withAlpha(191);

    public static final ARGB LIME_GREEN = new ARGB(255, 50, 205, 50);
    public static final ARGB LIME_GREEN_25 = LIME_GREEN.withAlpha(64);
    public static final ARGB LIME_GREEN_50 = LIME_GREEN.withAlpha(128);
    public static final ARGB LIME_GREEN_75 = LIME_GREEN.withAlpha(191);

    public static final ARGB PALE_GREEN = new ARGB(255, 152, 251, 152);
    public static final ARGB PALE_GREEN_25 = PALE_GREEN.withAlpha(64);
    public static final ARGB PALE_GREEN_50 = PALE_GREEN.withAlpha(128);
    public static final ARGB PALE_GREEN_75 = PALE_GREEN.withAlpha(191);

    public static final ARGB LIGHT_GREEN = new ARGB(255, 144, 238, 144);
    public static final ARGB LIGHT_GREEN_25 = LIGHT_GREEN.withAlpha(64);
    public static final ARGB LIGHT_GREEN_50 = LIGHT_GREEN.withAlpha(128);
    public static final ARGB LIGHT_GREEN_75 = LIGHT_GREEN.withAlpha(191);

    public static final ARGB MEDIUM_SPRING_GREEN = new ARGB(255, 0, 250, 154);
    public static final ARGB MEDIUM_SPRING_GREEN_25 = MEDIUM_SPRING_GREEN.withAlpha(64);
    public static final ARGB MEDIUM_SPRING_GREEN_50 = MEDIUM_SPRING_GREEN.withAlpha(128);
    public static final ARGB MEDIUM_SPRING_GREEN_75 = MEDIUM_SPRING_GREEN.withAlpha(191);

    public static final ARGB SPRING_GREEN = new ARGB(255, 0, 255, 127);
    public static final ARGB SPRING_GREEN_25 = SPRING_GREEN.withAlpha(64);
    public static final ARGB SPRING_GREEN_50 = SPRING_GREEN.withAlpha(128);
    public static final ARGB SPRING_GREEN_75 = SPRING_GREEN.withAlpha(191);

    public static final ARGB MEDIUM_SEA_GREEN = new ARGB(255, 60, 179, 113);
    public static final ARGB MEDIUM_SEA_GREEN_25 = MEDIUM_SEA_GREEN.withAlpha(64);
    public static final ARGB MEDIUM_SEA_GREEN_50 = MEDIUM_SEA_GREEN.withAlpha(128);
    public static final ARGB MEDIUM_SEA_GREEN_75 = MEDIUM_SEA_GREEN.withAlpha(191);

    public static final ARGB SEA_GREEN = new ARGB(255, 46, 139, 87);
    public static final ARGB SEA_GREEN_25 = SEA_GREEN.withAlpha(64);
    public static final ARGB SEA_GREEN_50 = SEA_GREEN.withAlpha(128);
    public static final ARGB SEA_GREEN_75 = SEA_GREEN.withAlpha(191);

    public static final ARGB FOREST_GREEN = new ARGB(255, 34, 139, 34);
    public static final ARGB FOREST_GREEN_25 = FOREST_GREEN.withAlpha(64);
    public static final ARGB FOREST_GREEN_50 = FOREST_GREEN.withAlpha(128);
    public static final ARGB FOREST_GREEN_75 = FOREST_GREEN.withAlpha(191);

    public static final ARGB DARK_GREEN = new ARGB(255, 0, 100, 0);
    public static final ARGB DARK_GREEN_25 = DARK_GREEN.withAlpha(64);
    public static final ARGB DARK_GREEN_50 = DARK_GREEN.withAlpha(128);
    public static final ARGB DARK_GREEN_75 = DARK_GREEN.withAlpha(191);

    public static final ARGB DARK_SEA_GREEN = new ARGB(255, 143, 188, 143);
    public static final ARGB DARK_SEA_GREEN_25 = DARK_SEA_GREEN.withAlpha(64);
    public static final ARGB DARK_SEA_GREEN_50 = DARK_SEA_GREEN.withAlpha(128);
    public static final ARGB DARK_SEA_GREEN_75 = DARK_SEA_GREEN.withAlpha(191);

    public static final ARGB YELLOW_GREEN = new ARGB(255, 154, 205, 50);
    public static final ARGB YELLOW_GREEN_25 = YELLOW_GREEN.withAlpha(64);
    public static final ARGB YELLOW_GREEN_50 = YELLOW_GREEN.withAlpha(128);
    public static final ARGB YELLOW_GREEN_75 = YELLOW_GREEN.withAlpha(191);

    public static final ARGB OLIVE_DRAB = new ARGB(255, 107, 142, 35);
    public static final ARGB OLIVE_DRAB_25 = OLIVE_DRAB.withAlpha(64);
    public static final ARGB OLIVE_DRAB_50 = OLIVE_DRAB.withAlpha(128);
    public static final ARGB OLIVE_DRAB_75 = OLIVE_DRAB.withAlpha(191);

    public static final ARGB OLIVE = new ARGB(255, 128, 128, 0);
    public static final ARGB OLIVE_25 = OLIVE.withAlpha(64);
    public static final ARGB OLIVE_50 = OLIVE.withAlpha(128);
    public static final ARGB OLIVE_75 = OLIVE.withAlpha(191);

    public static final ARGB DARK_OLIVE_GREEN = new ARGB(255, 85, 107, 47);
    public static final ARGB DARK_OLIVE_GREEN_25 = DARK_OLIVE_GREEN.withAlpha(64);
    public static final ARGB DARK_OLIVE_GREEN_50 = DARK_OLIVE_GREEN.withAlpha(128);
    public static final ARGB DARK_OLIVE_GREEN_75 = DARK_OLIVE_GREEN.withAlpha(191);

    public static final ARGB TEAL = new ARGB(255, 0, 128, 128);
    public static final ARGB TEAL_25 = TEAL.withAlpha(64);
    public static final ARGB TEAL_50 = TEAL.withAlpha(128);
    public static final ARGB TEAL_75 = TEAL.withAlpha(191);

    public static final ARGB SADDLE_BROWN = new ARGB(255, 139, 69, 19);
    public static final ARGB SADDLE_BROWN_25 = SADDLE_BROWN.withAlpha(64);
    public static final ARGB SADDLE_BROWN_50 = SADDLE_BROWN.withAlpha(128);
    public static final ARGB SADDLE_BROWN_75 = SADDLE_BROWN.withAlpha(191);

    public static final ARGB SIENNA = new ARGB(255, 160, 82, 45);
    public static final ARGB SIENNA_25 = SIENNA.withAlpha(64);
    public static final ARGB SIENNA_50 = SIENNA.withAlpha(128);
    public static final ARGB SIENNA_75 = SIENNA.withAlpha(191);

    public static final ARGB BROWN = new ARGB(255, 165, 42, 42);
    public static final ARGB BROWN_25 = BROWN.withAlpha(64);
    public static final ARGB BROWN_50 = BROWN.withAlpha(128);
    public static final ARGB BROWN_75 = BROWN.withAlpha(191);

    public static final ARGB CHOCOLATE = new ARGB(255, 210, 105, 30);
    public static final ARGB CHOCOLATE_25 = CHOCOLATE.withAlpha(64);
    public static final ARGB CHOCOLATE_50 = CHOCOLATE.withAlpha(128);
    public static final ARGB CHOCOLATE_75 = CHOCOLATE.withAlpha(191);

    public static final ARGB PERU = new ARGB(255, 205, 133, 63);
    public static final ARGB PERU_25 = PERU.withAlpha(64);
    public static final ARGB PERU_50 = PERU.withAlpha(128);
    public static final ARGB PERU_75 = PERU.withAlpha(191);

    public static final ARGB SANDY_BROWN = new ARGB(255, 244, 164, 96);
    public static final ARGB SANDY_BROWN_25 = SANDY_BROWN.withAlpha(64);
    public static final ARGB SANDY_BROWN_50 = SANDY_BROWN.withAlpha(128);
    public static final ARGB SANDY_BROWN_75 = SANDY_BROWN.withAlpha(191);

    public static final ARGB DARK_GOLDENROD = new ARGB(255, 184, 134, 11);
    public static final ARGB DARK_GOLDENROD_25 = DARK_GOLDENROD.withAlpha(64);
    public static final ARGB DARK_GOLDENROD_50 = DARK_GOLDENROD.withAlpha(128);
    public static final ARGB DARK_GOLDENROD_75 = DARK_GOLDENROD.withAlpha(191);

    public static final ARGB GOLDENROD = new ARGB(255, 218, 165, 32);
    public static final ARGB GOLDENROD_25 = GOLDENROD.withAlpha(64);
    public static final ARGB GOLDENROD_50 = GOLDENROD.withAlpha(128);
    public static final ARGB GOLDENROD_75 = GOLDENROD.withAlpha(191);

    public static final ARGB BURLY_WOOD = new ARGB(255, 222, 184, 135);
    public static final ARGB BURLY_WOOD_25 = BURLY_WOOD.withAlpha(64);
    public static final ARGB BURLY_WOOD_50 = BURLY_WOOD.withAlpha(128);
    public static final ARGB BURLY_WOOD_75 = BURLY_WOOD.withAlpha(191);

    public static final ARGB TAN = new ARGB(255, 210, 180, 140);
    public static final ARGB TAN_25 = TAN.withAlpha(64);
    public static final ARGB TAN_50 = TAN.withAlpha(128);
    public static final ARGB TAN_75 = TAN.withAlpha(191);

    public static final ARGB ROSY_BROWN = new ARGB(255, 188, 143, 143);
    public static final ARGB ROSY_BROWN_25 = ROSY_BROWN.withAlpha(64);
    public static final ARGB ROSY_BROWN_50 = ROSY_BROWN.withAlpha(128);
    public static final ARGB ROSY_BROWN_75 = ROSY_BROWN.withAlpha(191);

    public static final ARGB WHEAT = new ARGB(255, 245, 222, 179);
    public static final ARGB WHEAT_25 = WHEAT.withAlpha(64);
    public static final ARGB WHEAT_50 = WHEAT.withAlpha(128);
    public static final ARGB WHEAT_75 = WHEAT.withAlpha(191);

    public static final ARGB MAROON = new ARGB(255, 128, 0, 0);
    public static final ARGB MAROON_25 = MAROON.withAlpha(64);
    public static final ARGB MAROON_50 = MAROON.withAlpha(128);
    public static final ARGB MAROON_75 = MAROON.withAlpha(191);

    public static final ARGB SILVER = new ARGB(255, 192, 192, 192);
    public static final ARGB SILVER_25 = SILVER.withAlpha(64);
    public static final ARGB SILVER_50 = SILVER.withAlpha(128);
    public static final ARGB SILVER_75 = SILVER.withAlpha(191);

    public static final ARGB LIGHT_GRAY = new ARGB(255, 211, 211, 211);
    public static final ARGB LIGHT_GRAY_25 = LIGHT_GRAY.withAlpha(64);
    public static final ARGB LIGHT_GRAY_50 = LIGHT_GRAY.withAlpha(128);
    public static final ARGB LIGHT_GRAY_75 = LIGHT_GRAY.withAlpha(191);

    public static final ARGB DARK_GRAY = new ARGB(255, 169, 169, 169);
    public static final ARGB DARK_GRAY_25 = DARK_GRAY.withAlpha(64);
    public static final ARGB DARK_GRAY_50 = DARK_GRAY.withAlpha(128);
    public static final ARGB DARK_GRAY_75 = DARK_GRAY.withAlpha(191);

    public static final ARGB GRAY = new ARGB(255, 128, 128, 128);
    public static final ARGB GRAY_25 = GRAY.withAlpha(64);
    public static final ARGB GRAY_50 = GRAY.withAlpha(128);
    public static final ARGB GRAY_75 = GRAY.withAlpha(191);

    public static final ARGB DIM_GRAY = new ARGB(255, 105, 105, 105);
    public static final ARGB DIM_GRAY_25 = DIM_GRAY.withAlpha(64);
    public static final ARGB DIM_GRAY_50 = DIM_GRAY.withAlpha(128);
    public static final ARGB DIM_GRAY_75 = DIM_GRAY.withAlpha(191);

    public static final ARGB LIGHT_SLATE_GRAY = new ARGB(255, 119, 136, 153);
    public static final ARGB LIGHT_SLATE_GRAY_25 = LIGHT_SLATE_GRAY.withAlpha(64);
    public static final ARGB LIGHT_SLATE_GRAY_50 = LIGHT_SLATE_GRAY.withAlpha(128);
    public static final ARGB LIGHT_SLATE_GRAY_75 = LIGHT_SLATE_GRAY.withAlpha(191);

    public static final ARGB SLATE_GRAY = new ARGB(255, 112, 128, 144);
    public static final ARGB SLATE_GRAY_25 = SLATE_GRAY.withAlpha(64);
    public static final ARGB SLATE_GRAY_50 = SLATE_GRAY.withAlpha(128);
    public static final ARGB SLATE_GRAY_75 = SLATE_GRAY.withAlpha(191);

    public static final ARGB DARK_SLATE_GRAY = new ARGB(255, 47, 79, 79);
    public static final ARGB DARK_SLATE_GRAY_25 = DARK_SLATE_GRAY.withAlpha(64);
    public static final ARGB DARK_SLATE_GRAY_50 = DARK_SLATE_GRAY.withAlpha(128);
    public static final ARGB DARK_SLATE_GRAY_75 = DARK_SLATE_GRAY.withAlpha(191);

    public static final ARGB TRANSPARENT = new ARGB(0, 0, 0, 0);
    public static final ARGB TRANSPARENT_25 = TRANSPARENT.withAlpha(64);
    public static final ARGB TRANSPARENT_50 = TRANSPARENT.withAlpha(128);
    public static final ARGB TRANSPARENT_75 = TRANSPARENT.withAlpha(191);
}