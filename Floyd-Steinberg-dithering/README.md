# Floyd-Steinberg-dithering_DIP

Floyd–Steinberg dithering is an image dithering algorithm first published in 1976 by Robert W. Floyd and Louis Steinberg. It is commonly used by image manipulation software, for example when an image is converted into GIF format that is restricted to a maximum of 256 colors.

Floyd-Steinberg dithering is a truly magical technique. It is supposed to fool your eye and brain to make you think that you see more than there really is to be seen.


<img src="https://miro.medium.com/max/1192/1*c2BRVBKjKsEV57CcB7eUWw.png" title=""/>


In general, dither is method to reduce color space of an image by adding an artificial noise. The key idea is that the amount of light in an area should remain about the same.

The algorithm achieves dithering using error diffusion, meaning it pushes (adds) the residual quantization error of a pixel onto its neighboring pixels, to be dealt with later. It spreads the debt out according to the distribution (shown as a map of the neighboring pixels)

Floyd-Steinberg uses non-uniform distribution of quantization error to surrounding pixels. It means that the center pixel is rounded to 0 or 1. The residual error is then added to surrounding pixels.


<img src="https://miro.medium.com/max/962/1*jnMevWEIvo-iy6KIyx56gw.png" title=""/></a>

The pixel indicates the pixel currently being scanned, and the blank pixels are the previously-scanned pixels. The algorithm scans the image from left to right, top to bottom, quantizing pixel values one by one. Each time the quantization error is transferred to the neighboring pixels, while not affecting the pixels that already have been quantized. Hence, if a number of pixels have been rounded downwards, it becomes more likely that the next pixel is rounded upwards, such that on average, the quantization error is close to zero.

The diffusion coefficients have the property that if the original pixel values are exactly halfway in between the nearest available colors, the dithered result is a checkerboard pattern. For example, 50% grey data could be dithered as a black-and-white checkerboard pattern. For optimal dithering, the counting of quantization errors should be in sufficient accuracy to prevent rounding errors from affecting the result.

In some implementations, the horizontal direction of scan alternates between lines; this is called "serpentine scanning" or boustrophedon transform dithering.

In the following pseudocode we can see the algorithm described above. This works for any approximately linear encoding of pixel values, such as 8-bit integers, 16-bit integers or real numbers.


```python
 for y in range(0, height - 1):
        for x in range(1, width - 1):

            oldred, oldgreen, oldblue = rgb_image.getpixel((x ,y))

            new_red = int(round(factor * oldred / 255) * (255 / factor))
            new_green = int(round(factor * oldgreen /255) * (255 / factor))
            new_blue = int(round(factor * oldblue /255) * (255 / factor))

            rgb_image.putpixel((x, y), (new_red, new_green, new_blue))  # set it again to the corresponding pixel value

            red_error = oldred - new_red
            green_error = oldgreen - new_green
            blue_error = oldblue - new_green

            red, green, blue = rgb_image.getpixel((x + 1, y)) # get Red, Green , Blue of each pixel

            red += (red_error * 7 / 16.0)
            green += (green_error * 7 / 16.0)
            blue += (blue_error * 7 / 16.0)
            rgb_image.putpixel((x + 1 , y), (int(red), int(green), int(blue) )) #set it again to the corresponding pixel value

            red, green, blue = rgb_image.getpixel((x - 1, y + 1))  # get Red, Green , Blue of each pixel

            red += (red_error * 3 / 16.0)
            green += (green_error * 3 / 16.0)
            blue += (blue_error * 3 / 16.0)
            rgb_image.putpixel((x - 1, y + 1), (int(red), int(green), int(blue)))  # set it again to the corresponding pixel value

            red, green, blue = rgb_image.getpixel((x, y + 1))  # get Red, Green , Blue of each pixel

            red += (red_error * 5 / 16.0)
            green += (green_error * 5 / 16.0)
            blue += (blue_error * 5 / 16.0)
            rgb_image.putpixel((x, y + 1), (int(red), int(green), int(blue)))  # set it again to the corresponding pixel value

            red, green, blue = rgb_image.getpixel((x + 1, y + 1))  # get Red, Green , Blue of each pixel

            red += (red_error * 1 / 16.0)
            green += (green_error * 1 / 16.0)
            blue += (blue_error * 1 / 16.0)
            rgb_image.putpixel((x + 1, y + 1), (int(red), int(green), int(blue)))  # set it again to the corresponding pixel value

```
