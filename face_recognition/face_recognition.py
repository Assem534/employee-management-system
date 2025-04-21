import cv2
import face_recognition
import numpy as np
import os
import psycopg2
from datetime import datetime

# === DATABASE CONNECTION ===
conn = psycopg2.connect(
    host="localhost",
    port=5432,
    database="company system",
    user="postgres",
    password="1442009"
)
cursor = conn.cursor()

# === IMAGE ENCODING ===
path = 'image'
images = []
classNames = []

myList = os.listdir(path)
for cl in myList:
    curImg = cv2.imread(f'{path}/{cl}')
    if curImg is not None:
        images.append(curImg)
        classNames.append(os.path.splitext(cl)[0])

def findEncodings(images):
    encodList = []
    for img in images:
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        encode = face_recognition.face_encodings(img)
        if encode:
            encodList.append(encode[0])
    return encodList

# === ATTENDANCE TO DATABASE ===
emp_name = ""
def markAttendance(name):
    global emp_name
    current_date = datetime.now().date()
    current_time = datetime.now().strftime('%I:%M:%p')
    emp_id = int(name)

    cursor.execute("SELECT name FROM employees WHERE employeeid = %s", (emp_id,))
    emp_result = cursor.fetchone()

    if not emp_result:
        print(f"No employee found with ID {emp_id}")
        return

    emp_name = emp_result[0]

    cursor.execute("""
        SELECT * FROM attendances 
        WHERE employeeid = %s AND date = %s
    """, (emp_id, current_date))
    result = cursor.fetchone()

    if result:
        print(f"{emp_name} has already logged in today at {result[2]}.")
    else:
        cursor.execute("""
            INSERT INTO attendances (employeeid, name, time, date) 
            VALUES (%s, %s, %s, %s)
        """, (emp_id, emp_name, current_time, current_date))
        conn.commit()
        print(f"Attendance recorded for {emp_name} at {current_time} on {current_date}")

# === FACE RECOGNITION ===
encodingListKnow = findEncodings(images)
print("Encoding images: Done")

cap = cv2.VideoCapture(0)

process_frame = True  

while True:
    success, img = cap.read()
    if not success:
        break

    imgS = cv2.resize(img, (0, 0), fx=0.25, fy=0.25)
    imgS = cv2.cvtColor(imgS, cv2.COLOR_BGR2RGB)

    if process_frame:
        faceCurFrame = face_recognition.face_locations(imgS)
        encodeCurFrame = face_recognition.face_encodings(imgS, faceCurFrame)

        for encodeface, locface in zip(encodeCurFrame, faceCurFrame):
            matches = face_recognition.compare_faces(encodingListKnow, encodeface)
            faceDis = face_recognition.face_distance(encodingListKnow, encodeface)

            if len(faceDis) > 0:
                matchindex = np.argmin(faceDis)
                if matches[matchindex]:
                    id = classNames[matchindex].upper()
                else:
                    id = "UNKNOWN"
            else:
                id = "UNKNOWN"

            color = (0, 255, 0) if id != "UNKNOWN" else (0, 0, 255)

            y1, x2, y2, x1 = locface
            # Scale up back because we resized the input image
            y1, x2, y2, x1 = y1*4, x2*4, y2*4, x1*4
            cv2.rectangle(img, (x1, y1), (x2, y2), color, 2)
            cv2.putText(img, id + "-" + emp_name, (x1, y1 - 6), cv2.FONT_HERSHEY_DUPLEX, 1, color, 2)

            if id != "UNKNOWN":
                markAttendance(id)

    process_frame = not process_frame  # Process alternate frames only

    cv2.imshow('Webcam', img)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
cursor.close()
conn.close()
