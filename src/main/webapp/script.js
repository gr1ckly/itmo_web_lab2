let y;
const formWithData = document.getElementById("form");
formWithData.addEventListener('submit', submitHandler);
const canvas = document.getElementById("graphic");
const ctx = canvas.getContext("2d");
const scale = (Math.min(canvas.width, canvas.height) / 3);
const xCenter = canvas.width / 2;
const yCenter = canvas.height / 2;
console.log(ctx);
canvas.addEventListener("click", function (event) {
    let r = getR(new FormData(formWithData));
    if (isNaN(r)){
        invalidR();
    } else {
        let curr_x = event.offsetX;
        let curr_y = event.offsetY;
        curr_x -= xCenter;
        curr_y -= yCenter;
        curr_x /= scale;
        curr_y /= scale;
        let x = curr_x * r;
        y = -curr_y * r;
        sendRequest(x, y, r);
    }
});
drawGraphic(ctx);

const rSelect = document.getElementById("r-select");
rSelect.addEventListener("change", (event) => {
    drawGraphic(ctx);
})

function yButtonHandler(value){
    let yButtonList = document.getElementById("Y-button").getElementsByTagName("input");
    for (let i = 0; i < yButtonList.length; i++){
        if (yButtonList[i].getAttribute("value") == value) {
            yButtonList[i].style.background = "#888";
        }
        else {
            yButtonList[i].style.background = "#333";
            yButtonList[i].style.color = "white";
        }
    }
    y = value;
}

function drawGraphic(ctx){
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.strokeStyle = "black";
    ctx.lineWidth = "2";
    console.log(xCenter, yCenter, scale);

    ctx.beginPath();
    ctx.arc(xCenter, yCenter, scale, 0, -Math.PI / 2, true);
    ctx.lineTo(xCenter, yCenter);
    ctx.closePath();

    ctx.fillStyle = "lightblue"
    ctx.fill();

    ctx.fillRect(xCenter - scale, yCenter, scale, scale / 2);

    ctx.beginPath();
    ctx.moveTo(xCenter, yCenter);
    ctx.lineTo(xCenter + scale / 2, yCenter);
    ctx.lineTo(xCenter, yCenter + scale);
    ctx.closePath();

    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(0, yCenter);
    ctx.lineTo(canvas.width, yCenter);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(xCenter, 0);
    ctx.lineTo(xCenter, canvas.height);
    ctx.stroke();

    ctx.textAlign = "left";
    ctx.textBaseline = "middle";
    ctx.fillStyle = "black";
    ctx.font = "14px Arial";
    let r = getR(new FormData(formWithData));
    if (isNaN(r)) {
        ctx.fillText("-R", xCenter + 7, yCenter + scale);
        ctx.fillText("R", xCenter + 7, yCenter - scale);
        ctx.fillText("-R/2", xCenter + 7, yCenter + scale / 2);
        ctx.fillText("R/2", xCenter + 7, yCenter - scale / 2);

        ctx.fillText("-R", xCenter - scale, yCenter - 10);
        ctx.fillText("R", xCenter + scale, yCenter - 10);
        ctx.fillText("-R/2", xCenter - scale / 2, yCenter - 10);
        ctx.fillText("R/2", xCenter + scale / 2, yCenter - 10);
    } else {
        ctx.fillText(-r, xCenter + 7, yCenter + scale);
        ctx.fillText(r, xCenter + 7, yCenter - scale);
        ctx.fillText(-r / 2, xCenter + 7, yCenter + scale / 2);
        ctx.fillText(r / 2, xCenter + 7, yCenter - scale / 2);

        ctx.fillText(-r, xCenter - scale, yCenter + 10);
        ctx.fillText(r, xCenter + scale, yCenter + 10);
        ctx.fillText(-r / 2, xCenter - scale / 2, yCenter + 10);
        ctx.fillText(r / 2, xCenter + scale / 2, yCenter + 10);

        let points = document.getElementsByClassName("table-line");
        for (let point of points){
            let xTable = point.querySelectorAll(".x-table")[0].innerText;
            let yTable = point.querySelectorAll(".y-table")[0].innerText;
            let hittingTable = point.querySelectorAll(".hitting-table")[0].innerText;
            if (hittingTable === "true") {
                drawPoint(ctx, parseFloat(xTable), parseFloat(yTable), r, "green");
            } else {
                drawPoint(ctx, parseFloat(xTable), parseFloat(yTable), r, "red");
            }
        }
    }
}

function invalidR(){
    let errDiv = document.getElementById("rError-message");
    errDiv.style.display = "inline";
}

function invalidX (){
    let errDiv = document.getElementById("xError-message");
    errDiv.style.display = "inline";
}

function invalidY() {
    let errDiv = document.getElementById("yError-message");
    errDiv.style.display = "inline";
}

function getR(formData){
    return parseFloat(formData.get("r"))
}

function clearFull(){
    let XerrDiv = document.getElementById("xError-message")[0];
    XerrDiv.style.display = "none";
    let YerrDiv = document.getElementById("yError-message")[0];
    YerrDiv.style.display = "none";
    let RerrDiv = document.getElementById("rError-message")[0];
    RerrDiv.style.display = "none";
    document.getElementById("X").value = "";
    clearY();
}

function clearY(){
    y = null;
    let yButtonList = document.getElementById("Y-button").getElementsByTagName("input");
    for (let i = 0; i < yButtonList.length; i++){
        yButtonList[i].style.background = "#333";
        yButtonList[i].style.color = "white";
    }
}

function getX(formData){
    let value = formData.get("x");
    if (value!= null && value.match(/^-?\d+(\.\d+)?$/) && -3 <= value && value <= 5) return parseFloat(value);
    else {
        return null;
    }
}

function submitHandler(event){
    event.preventDefault();
    let formData = new FormData(formWithData);
    let x = getX(formData);
    let r = getR(formData);
    if (!isNaN(r) && y!= undefined  && y != null && x != null) sendRequest(x, y, r);
    else {
        console.log(false);
        if (typeof(r) != "number") invalidR();
        if (y === null || y === undefined) invalidY();
        if (x === null) invalidX();
    }
}

async function sendRequest(x, y, r){
    let data = {x: x, y: y, r: r};
    console.log(data);
    let response = await fetch("../controller", {
        method: "POST",
        headers: {
            "Content-type": "application/json",
        },
        body: JSON.stringify(data)
    });
    if (response.ok){
        document.body.innerHTML = await response.text();
    } else {
        console.log(response.status);
    }
    clearFull();
}

function drawPoint(ctx, x, y, r, color){
    ctx.fillStyle = color;
    if (!isNaN(r)) {
        let scaleX = xCenter + (x / r) * scale;
        let scaleY = yCenter - (y / r) * scale;
        console.log(scaleX, scaleY);
        ctx.beginPath();
        ctx.arc(scaleX, scaleY, 5, 0, Math.PI * 2);
        ctx.fill();
    }
}