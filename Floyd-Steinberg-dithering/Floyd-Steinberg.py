import PIL
from PIL import Image, ImageColor

def floydSteinberg(image, factor):
    rgb_image = image.convert('RGB')  # convert the image to RGB
    width, height = rgb_image.size # get the Width and heigth of image

    if factor == 0:
        print(" ERROR !")
        return rgb_image

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


    return rgb_image

def grayScale(image):
    width, height = image.size # get the Width and heigth of image
    rgb_image = image.convert('RGB') # convert the image to RGB
    for y in range(0, height):
        for x in range(0, width):
            red, green, blue = rgb_image.getpixel((x, y)) # get Red, Green , Blue of each pixel
            red *= 0.299  #Used 30% of Red
            green *= 0.587 #Used 59% of Green
            blue *= 0.144 # used 11% of Blue
            sum = int (red + green + blue)  #Sum all these three values of color
            image.putpixel((x, y), (sum, sum, sum)) #set it again to the corresponding pixel value

    return image

def main():
    image = PIL.Image.open("ba.jpg") # Open method used to open different extension image file
    gray = floydSteinberg(image, 1)
    gray.show()



main()