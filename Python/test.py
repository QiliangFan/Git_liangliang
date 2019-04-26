import pytesseract
from PIL import Image

pytesseract.pytesseract.tesseract_cmd=r'D:/Tesseract/tesseract.exe'
image = Image.open(r'./test1.png')
#text = pytesseract.image_to_string(image, lang='chi_sim')
text = pytesseract.image_to_string(image, lang='chi_sim')

print(text)