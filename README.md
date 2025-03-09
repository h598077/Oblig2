Testcase:
clicking a button in the main-menu (if you have one) takes you to the right sub-activity (i.e. to the Quiz or the Gallery; testing one button is enough); 
<br>**Uses MainActivity, it has passed.**

is the score updated correctly in the quiz (the test submits at least one right/wrong answer each and you check if the score is correct afterwards); 
<br>**Uses GalleryActivity, it has passed**

a test that checks that the number of registered pictures/persons is correct after adding/deleting an entry. For adding, use Intent Stubbing to return some image data (e.g. from the resource-folder) without any user interaction; 
<br>**Uses QuizActivity, did not pass because did not find a way to know the right answear.**
