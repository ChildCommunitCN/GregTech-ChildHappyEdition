package gregtech.api.util;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;

public class GradientUtil {

    private GradientUtil() {
    }

    public static Pair<Color, Color> getGradient(Color rgb, int luminanceDifference) {
        float[] hsl = RGBtoHSL(rgb);
        float l = hsl[2];
        System.out.println("R: " + rgb.getRed() + " G: " + rgb.getGreen() + " B: " + rgb.getBlue());
        System.out.println("H: " + hsl[0] + " S: " + hsl[1] + " L: " + hsl[2]);

        float[] upshade = new float[3];
        float[] downshade = new float[3];
        System.arraycopy(hsl, 0, upshade, 0, 3);
        System.arraycopy(hsl, 0, downshade, 0, 3);
        upshade[2] = upshade[2] + luminanceDifference;
        if (upshade[2] > 100.0F) upshade[2] = 100.0F;
        downshade[2] = downshade[2] - luminanceDifference;
        if (downshade[2] < 0.0F) downshade[2] = 0.0F;

        Color upshadeRgb = toRGB(upshade);
        Color downshadeRgb = toRGB(downshade);

        System.out.println("Upshade: ");
        System.out.println("H: " + upshade[0] + " S: " + upshade[1] + " L: " + upshade[2]);
        System.out.println("R: " + upshadeRgb.getRed() + " G: " + upshadeRgb.getGreen() + " B: " + upshadeRgb.getBlue());
        System.out.println("Downshade: ");
        System.out.println("H: " + downshade[0] + " S: " + downshade[1] + " L: " + downshade[2]);
        System.out.println("R: " + downshadeRgb.getRed() + " G: " + downshadeRgb.getGreen() + " B: " + downshadeRgb.getBlue());
        return Pair.of(downshadeRgb, upshadeRgb);
    }

    public static Pair<Color, Color> getGradient(int rgb, int luminanceDifference) {
        return getGradient(new Color(rgb), luminanceDifference);
    }

    public static float[] RGBtoHSL(Color rgbColor) {
        // Get RGB values in the range 0 - 1
        float[] rgb = rgbColor.getRGBColorComponents(null);
        float r = rgb[0];
        float g = rgb[1];
        float b = rgb[2];

        // Minimum and Maximum RGB values are used in the HSL calculations
        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));

        // Calculate the Hue
        float h = 0;
        if (max == min) {
            h = 0;
        } else if (max == r) {
            h = ((60 * (g - b) / (max - min)) + 360) % 360;
        } else if (max == g) {
            h = (60 * (b - r) / (max - min)) + 120;
        } else if (max == b) {
            h = (60 * (r - g) / (max - min)) + 240;
        }

        // Calculate the Luminance
        float l = (max + min) / 2;

        // Calculate the Saturation
        float s;
        if (max == min) {
            s = 0;
        } else if (l <= 0.5F) {
            s = (max - min) / (max + min);
        } else {
            s = (max - min) / (2 - max - min);
        }

        return new float[] {h, s * 100, l * 100};
    }

    public static Color toRGB(float[] hsv) {
        return toRGB(hsv[0], hsv[1], hsv[2]);
    }

    public static Color toRGB(float h, float s, float l) {
        // Formula needs all values between 0 - 1
        h = h % 360.0F;
        h /= 360.0F;
        s /= 100.0F;
        l /= 100.0F;

        float q;
        if (l < 0.5F) {
            q = l * (1 + s);
        } else {
            q = (l + s) - (s * l);
        }

        float p = 2 * l - q;

        float r = Math.max(0, hueToRGB(p, q, h + (1.0F / 3.0F)));
        float g = Math.max(0, hueToRGB(p, q, h));
        float b = Math.max(0, hueToRGB(p, q, h - (1.0F / 3.0F)));

        r = Math.min(r, 1.0F);
        g = Math.min(g, 1.0F);
        b = Math.min(b, 1.0F);

        return new Color(r, g, b);
    }

    private static float hueToRGB(float p, float q, float h) {
        if (h < 0) {
            h += 1;
        }
        if (h > 1) {
            h -= 1;
        }
        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }
        if (2 * h < 1) {
            return  q;
        }
        if (3 * h < 2) {
            return p + ( (q - p) * 6 * ((2.0F / 3.0F) - h) );
        }
        return p;
    }
}
