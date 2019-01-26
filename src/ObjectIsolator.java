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

    private void flipValues() { //incase you try to isolate someone with dark skin
        for (int r = 0; r < image.length; r++) {
            for (int c = 0; c < image[r].length; c++) {
                if (image[r][c] == 255) {
                    image[r][c] = 0;
                } else {
                    image[r][c] = 255;
                }
            }
        }
    }

    public short[][] filter(short[][] pixels, boolean darkPeople) {

        this.image = pixels;
        this.image = blurKernel.convolve(image);
        setThreshold(threshold);

        if (darkPeople) flipValues();

        return image;
    }


}
