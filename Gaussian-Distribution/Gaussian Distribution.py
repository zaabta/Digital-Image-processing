import math
import numpy as np
from PIL import Image


class GaussianBlur():

    def gaussianModel(self,x, y, variance):
         return (1 / (2 * math.pi * variance ** 2)) * math.exp(-(x ** 2 + y ** 2) / (2 * variance **2))

    def generateWeightMatrix(self, Radius, variance):
        self.weightsMatrix = np.zeros((Radius, Radius))
        self.summlation = 0.0
        for i in range(0,len(self.weightsMatrix)):
            for j in range(0, len (self.weightsMatrix[i])):
                self.weightsMatrix[i][j] = self.gaussianModel(i - Radius / 2, j - Radius / 2, variance)
                self.summlation += self.weightsMatrix[i][j]


        return self.weightsMatrix


    def normalizedSum(self):
        self.normalizedSum = 0.0
        for i in range(0, len(self.weightsMatrix)):
            for j in range(0, len(self.weightsMatrix[i])):
                self.weightsMatrix[i][j] /= self.summlation
                self.normalizedSum += self.weightsMatrix[i][j]
        return self.normalizedSum

    def printWeightedMatrixToFile(self, weightMatrix):
         Max = 0
         for i in range(0, len(weightMatrix)):
             for j in range(0, len(weightMatrix[i])):
                 Max = max(Max, weightMatrix[i][j])

         new_image = Image.fromarray(weightMatrix, 'RGB')

         for i in range(0, len(weightMatrix)):
             for j in range(0, len(weightMatrix[i])):
                 grayScaleValue = int ((weightMatrix[i][j] / Max) * 255)
                 new_image.putpixel((i, j), (grayScaleValue, grayScaleValue, grayScaleValue))
         new_image.show()

    def creatGaussianImage(self, image, weights, radius):
         print("Working ... !")

         image = image.convert('RGB')
         width, height = image.size
         answer = Image.new('RGB', (width, height))
         for x in range(0, width - radius):
             for y in range(0, height - radius):
                 distributedColorRed = np.zeros((radius, radius))
                 distributedColorGreen = np.zeros((radius, radius))
                 distributedColorBlue = np.zeros((radius,radius))

                 for weightX in range(0, len(weights)):
                     for weightY in range(0, len(weights[weightX])):

                         sampleX = int(x + weightX - (len(weights) / 2))
                         sampleY = int(y + weightY - (len(weights) / 2))
                         # Check sampleX & sampleY  are Out of Bounds

                         if sampleX > (width - 1):
                             error_offser = int(sampleX - (width - 1))
                             sampleX = (width -1) - error_offser
                         if sampleY > (height - 1):
                             error_offser = int(sampleY - (height - 1))
                             sampleY = (height - 1) - error_offser

                         if sampleX < 0:
                             sampleX = math.fabs(sampleX)
                         if sampleY < 0:
                             sampleY = math.fabs(sampleY)

                         currentWeight = weights[weightX][weightY]
                         red, green, blue = image.getpixel((sampleX, sampleY))
                         distributedColorRed[weightX][weightY] = currentWeight * red
                         distributedColorGreen[weightX][weightY] = currentWeight * green
                         distributedColorBlue[weightX][weightY] = currentWeight * blue


                 answer.putpixel((x, y), (self.getWeightColorValue(distributedColorRed)
                                          , self.getWeightColorValue(distributedColorGreen)
                                          , self.getWeightColorValue(distributedColorBlue)))
         print("Done !")
         return answer

    def getWeightColorValue(self,weightdColor):
          summation = 0.0
          for i in range(0, len(weightdColor)):
              for j in range(0, len(weightdColor[i])):
                  summation += weightdColor[i][j]

          return int(summation)



def main():
    Gaussian = GaussianBlur()
    weightMatrix = Gaussian.generateWeightMatrix(5, 1.5)
    Gaussian.printWeightedMatrixToFile(weightMatrix)
    image = Image.open("minion.jpg")
    new_image = Gaussian.creatGaussianImage(image, weightMatrix, 5)
    image.show()
    new_image.show()



main()
