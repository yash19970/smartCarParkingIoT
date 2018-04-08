def yield_times():
    from datetime import date, time, datetime, timedelta
    from random import randint
    while True:
    	start = datetime.combine(date.today(), time(randint(0, 23), 0))
    	yield start.strftime("%H:%M:%S")
        start += timedelta(seconds=127)
        yield start.strftime("%H:%M:%S")

def getDate():
	gen = yield_times()
	timeStampList = [gen.next()]
	return timeStampList