function calculateFOV(screenSize, distance, aspectRatio) {
    var screenHeight = Math.sqrt(Math.pow(screenSize, 2) / (Math.pow(aspectRatio, 2) + 1));
    var screenWidth = screenHeight * aspectRatio;
    
    var horizontalFOV = 2 * Math.atan((screenWidth / 2) / distance) * (180 / Math.PI);
    var verticalFOV = 2 * Math.atan((screenHeight / 2) / distance) * (180 / Math.PI);

    var $session = $jsapi.context().session;
    $session.horizontalFOV = Math.ceil(horizontalFOV);
    $session.verticalFOV = Math.ceil(verticalFOV);
}