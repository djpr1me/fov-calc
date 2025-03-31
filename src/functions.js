function calculateFOV(screenSize, distance) {
    var aspectRatio = 16 / 9;
    var screenHeight = Math.sqrt(Math.pow(screenSize, 2) / (Math.pow(aspectRatio, 2) + 1));
    var screenWidth = screenHeight * aspectRatio;
    
    var horizontalFOV = 2 * Math.atan((screenWidth / 2) / distance) * (180 / Math.PI);
    var verticalFOV = 2 * Math.atan((screenHeight / 2) / distance) * (180 / Math.PI);

    var $session = $jsapi.context().session;
    $session.horizontalFOV = horizontalFOV.toFixed(2);
    $session.verticalFOV = verticalFOV.toFixed(2);
}