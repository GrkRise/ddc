package com.grk_rise.rainbow.core;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by GrK_Rise on 17.03.2015.
 */
public class KMeans {
    public static final int BLACK = Color.rgb(0, 0, 0);
    public static final int WHITE = Color.rgb(255,255,255);

    public static final int RED = Color.rgb(255,0,0);
    public static final int ORANGE = Color.rgb(255,100,0);
    public static final int YELLOW = Color.rgb(255,255,0);
    public static final int GREEN = Color.rgb(0,255,0);
    public static final int LIGHTBLUE = Color.rgb(60,170,255);
    public static final int BLUE = Color.rgb(0,0,255);
    public static final int VIOLET = Color.rgb(194,0,255);

    public static final int GINGER = Color.rgb(215,125,49);
    public static final int PINK = Color.rgb(255,192,203);
    public static final int LIGHTGREEN = Color.rgb(153,255,153);
    public static final int BROWN = Color.rgb(150,75,0);
    public static final int CLUSTER_COUNT = 5;

    double rgb_euclidean(int a, ColorCluster b)
    {
        double red = (Color.red(a) - b.color_B)*(Color.red(a) - b.color_B);
        double green = (Color.green(a) - b.color_G)*(Color.green(a) - b.color_G);
        double blue = (Color.blue(a) - b.color_R)*(Color.blue(a) - b.color_R);

        return Math.sqrt(red + green + blue);
    }

    double rgb_euclidean(ColorCluster a, ColorCluster b)
    {
        double blue = (a.new_color_B - b.color_B)*(a.new_color_B - b.color_B);
        double green = (a.new_color_G - b.color_G)*(a.new_color_G - b.color_G);
        double red = (a.new_color_R - b.color_R)*(a.new_color_R - b.color_R);

        return Math.sqrt(red + green + blue);
    }

    private Bitmap bmp;
    ArrayList<ColorCluster> clusters = new ArrayList<ColorCluster>();


    public KMeans(Bitmap bmp) {

        this.bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 2, bmp.getHeight() / 2, true);
    }

    public void k_means(){
        int clusuter_count = CLUSTER_COUNT;

        Random random = new Random();

        for(int k = 0; k < clusuter_count; k++){
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);
            clusters.add(new ColorCluster(0, 0, 0, r, g, b, 0));
        }

        double min_rgb_euclidean = 0.0, old_rgb_euclidean = 0.0;

//--------------------------------------------------------------------------------------------------

        while(true){

            for(int k = 0; k < clusuter_count; k++){
                clusters.get(k).count = 1;
                clusters.get(k).color_R = clusters.get(k).new_color_R;
                clusters.get(k).color_G = clusters.get(k).new_color_G;
                clusters.get(k).color_B = clusters.get(k).new_color_B;
                clusters.get(k).new_color_R = 0;
                clusters.get(k).new_color_G = 0;
                clusters.get(k).new_color_B = 0;
            }

            for(int y = 0; y < bmp.getHeight(); y++) {
                for (int x = 0; x < bmp.getWidth(); x++) {
                    int px = bmp.getPixel(x, y);
                    min_rgb_euclidean = 255*255*255;
                    int cluster_index = -1;

                    for (int k = 0; k < clusuter_count; k++) {
                        double euclid = rgb_euclidean(px, clusters.get(k));
                        if (euclid < min_rgb_euclidean) {
                            min_rgb_euclidean = euclid;
                            cluster_index = k;
                        }

                        clusters.get(cluster_index).setCount(clusters.get(cluster_index).getCount() + 1);

                        clusters.get(cluster_index).new_color_B += Color.blue(px);
                        clusters.get(cluster_index).new_color_G += Color.green(px);
                        clusters.get(cluster_index).new_color_R += Color.red(px);
                    }
                }
            }

            min_rgb_euclidean = 0;
            for(int k = 0; k < clusuter_count; k++)
            {
                clusters.get(k).new_color_R = clusters.get(k).new_color_R / clusters.get(k).count;
                clusters.get(k).new_color_G = clusters.get(k).new_color_G / clusters.get(k).count;
                clusters.get(k).new_color_B = clusters.get(k).new_color_B / clusters.get(k).count;
                double eclair = rgb_euclidean(clusters.get(k), clusters.get(k));
                if(eclair > min_rgb_euclidean)
                    min_rgb_euclidean = eclair;
            }

            if(Math.abs(min_rgb_euclidean - old_rgb_euclidean) < 1) {
                break;
            }

            old_rgb_euclidean = min_rgb_euclidean;
        }

//--------------------------------------------------------------------------------------------------

        int[] colors = new int[bmp.getWidth()*bmp.getHeight()];
        int lines = 480/clusuter_count;
        int cols = 640;



        for (int i = 0; i < clusuter_count; i++) {
            Arrays.fill(colors,  i*lines*cols , cols * lines *(i+1),
                    Color.rgb(clusters.get(i).color_R, clusters.get(i).color_G,
                            clusters.get(i).color_B));
            Log.e("COLS", String.valueOf(clusters.get(i).color_R));
            Log.e("COLS", String.valueOf(clusters.get(i).color_G));
            Log.e("COLS", String.valueOf(clusters.get(i).color_B));
            Log.e("COLS", "-----------------");
            //Arrays.fill(colors, i*lines*cols , cols * lines *(i+1), Color.rgb(222, 100, 100 * i));

        }

        bmp = Bitmap.createBitmap(colors, cols, lines*clusuter_count, bmp.getConfig());


    }


    public Bitmap getRslt() {
        return bmp;
    }

    public class ColorCluster
    {
        private int color_R;
        private int color_G;
        private int color_B;

        private int new_color_R;
        private int new_color_G;
        private int new_color_B;

        private int count;


        public int getColor_R() {
            return color_R;
        }

        public void setColor_R(int color_R) {
            this.color_R = color_R;
        }

        public int getColor_G() {
            return color_G;
        }

        public void setColor_G(int color_G) {
            this.color_G = color_G;
        }

        public int getColor_B() {
            return color_B;
        }

        public void setColor_B(int color_B) {
            this.color_B = color_B;
        }

        public int getNew_color_R() {
            return new_color_R;
        }

        public void setNew_color_R(int new_color_R) {
            this.new_color_R = new_color_R;
        }

        public int getNew_color_G() {
            return new_color_G;
        }

        public void setNew_color_G(int new_color_G) {
            this.new_color_G = new_color_G;
        }

        public int getNew_color_B() {
            return new_color_B;
        }

        public void setNew_color_B(int new_color_B) {
            this.new_color_B = new_color_B;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ColorCluster(int color_R, int color_G, int color_B, int new_color_R, int new_color_G, int new_color_B, int count) {
            this.color_R = color_R;
            this.color_G = color_G;
            this.color_B = color_B;
            this.new_color_R = new_color_R;
            this.new_color_G = new_color_G;
            this.new_color_B = new_color_B;
            this.count = count;
        }
    }
}


