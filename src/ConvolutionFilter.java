public class ConvolutionFilter {

    private short[][] kernel;
    private int weight;

    public int getWeight() {
        return weight;
    }

    public short[][] getKernel() {
        return kernel;
    }

    public ConvolutionFilter(short[][] kernel) {
        this.kernel = kernel;

        int sum = 0;
        int totalBits = 0;

        for (int r = 0; r < kernel.length; r++) {
            for (int c = 0; c < kernel[r].length; c++) {
                sum += kernel[r][c];
                totalBits++;
            }
        }

        this.weight = sum;
    }

    public short[][] convolve(short[][] img) {
        short[][] copy = deepCopy(img);
        for (int r = 0; r < img.length - kernel.length; r++) {
            for (int c = 0; c < img[r].length - kernel[img[r].length % kernel.length].length; c++) {

                short newPixel = 0;
                for (int i = 0; i < kernel.length; i++) {
                    for (int j = 0; j < kernel[i].length; j++) {
                        newPixel += img[r + i][c + j] * kernel[i][j];
                    }
                }

                newPixel /= this.weight;
                copy[r][c] = newPixel;

            }
        }
        return copy;
    }

    private short[][] deepCopy(short[][] original) {
        short[][] copy = original.clone();
        for (int r = 0; r < copy.length; r++) {
            copy[r] = original[r].clone();
        }
        return copy;
    }
}
