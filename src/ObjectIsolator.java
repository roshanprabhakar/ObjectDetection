public class ObjectIsolator {

    private int threshold;
    private short image[][];

    private ConvolutionFilter blurKernel = new ConvolutionFilter(
            new short[][] {
                    {-1,-1,-1,-1,-1},
                    {-1,-1,-1,-1,-1},
                    {-1,-1, 8,-1,-1},
                    {-1,-1,-1,-1,-1},
                    {-1,-1,-1,-1,-1}
            }
    );

    public int getThreshold() {
        return threshold;
    }

    public short[][] getImage() {
        return image;
    }

    public ObjectIsolator(int threshhold) {
        this.threshold = threshhold; // used to identify skin color
    }

    private void setThreshold(int threshold) {
        for (int r = 0; r < image.length; r++) {
            for (int c = 0; c < image[r].length; c++) {
                image[r][c] = (image[r][c] < threshold) ? (short) 0 : 255;
            }
        }
    }

    public short[][] filter(short[][] pixels) {

        this.image = pixels;
        this.image = blurKernel.convolve(image);
        setThreshold(threshold);

        return image;
    }


}
