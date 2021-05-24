import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

import javax.imageio.ImageIO;

public class grayFilter {

	static int getindex(int x,int y,int width) {
		return x + y * width;
	}
	
	static BufferedImage  GrayScale(BufferedImage source_img) throws IOException {
BufferedImage image_result =null;
		
		image_result = new BufferedImage(source_img.getWidth(),source_img.getHeight(),BufferedImage.TYPE_INT_BGR);
		
		for(int y = 0 ; y < source_img.getHeight() ; y++) {
			for(int x = 0 ; x < source_img.getWidth() ; x++) {
				Color pix =new Color(source_img.getRGB(x, y));
				float Red=pix.getRed();
				float Blue=pix.getBlue();
				float Green=pix.getGreen();
				
				
			
				int newRed= (int) Math.round ((Red * 0.299));
				int newBlue=(int) Math.round ((Blue * 0.114));
				int newGreen=(int) Math.round ((Green * 0.587));
				
				int sum = newRed + newBlue +newGreen;
				
				image_result.setRGB(x, y, new Color(sum,sum,sum).getRGB());
				
			}
		}

		return image_result;
	}
	
	
	public static void main(String[] args) {
		BufferedImage image_result =null;
	
			BufferedImage source_img;
			try {
				source_img = ImageIO.read(new File("/Users/dabbaghie/eclipse-workspace/GrayFilter/ba.jpg"));
				image_result = new BufferedImage(source_img.getWidth(),source_img.getHeight(),BufferedImage.TYPE_INT_BGR);
				image_result = GrayScale(source_img);
				ImageIO.write(image_result, "PNG", new File("answer.png"));
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}

}
