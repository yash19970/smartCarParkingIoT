from faker.factory import Factory
import csv 
import generatetime as g
from random import randint
def writeTo_csv(fake):
    with open('test.csv', 'wb') as csvfile:
        write_to_csv = csv.writer(csvfile, quoting=csv.QUOTE_MINIMAL)
        write_to_csv.writerow(['Name', 'Time', 'Day'])
        firstName1 = "first_name"
        for i in range(4):
            firstNameList = [(firstName1, getattr(fake, firstName1)())]
            print "%s = %s" % (firstName1, getattr(fake, firstName1)())
            timeStampList = g.getDate()
            insertTime = timeStampList[0][0]+timeStampList[0][1]+timeStampList[0][2]+timeStampList[0][3]+timeStampList[0][4]
            weekday = generateWeekDay()
            write_to_csv.writerow((firstNameList[0][1],insertTime,weekday))

def generateWeekDay():
    day = randint(1, 7)
    if day ==1:
        dayStr = "Monday"
    elif day ==2:
        dayStr = "Tuesday"
    elif day ==3:
        dayStr = "Wednesday"
    elif day ==4:
        dayStr = "Thursday"
    elif day ==5:
        dayStr = "Friday"
    elif day ==6:
        dayStr = "Saturday"    
    elif day ==7:
        dayStr = "Sunday"

    return dayStr

if __name__ == "__main__":
    fake = Factory.create('en_GB')
    writeTo_csv(fake)