function chooseUser() {
    if (document.getElementById('1').checked) {
        window.open("admin_home.html", "_self");
    } else if (document.getElementById('2').checked) {
        window.open("mentor-home.html", "_self");
    } else if (document.getElementById('3').checked) {
        window.open("student_home.html", "_self");
    }
}