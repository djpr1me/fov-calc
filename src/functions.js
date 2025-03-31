function calculateFOV(screenSize, distance, aspectRatio) {
    var screenHeight = Math.sqrt(Math.pow(screenSize, 2) / (Math.pow(aspectRatio, 2) + 1));
    var screenWidth = screenHeight * aspectRatio;
    
    var horizontalFOVDegrees = 2 * Math.atan((screenWidth / 2) / distance) * (180 / Math.PI);
    var verticalFOVDegrees = 2 * Math.atan((screenHeight / 2) / distance) * (180 / Math.PI);

    var horizontalFOVRadians = 2 * Math.atan((screenWidth / 2) / distance);
    horizontalFOVRadians = horizontalFOVRadians.toFixed(4);
    
    var $session = $jsapi.context().session;
    $session.horizontalFOV = Math.ceil(horizontalFOVDegrees);
    $session.verticalFOV = Math.ceil(verticalFOVDegrees);
    $session.horizontalFOVRadians = horizontalFOVRadians;
    $session.dirtRallyFOV = Math.ceil(verticalFOVDegrees * 2);
    var f1BaseFOV = 77;
    var f1Step = 1;
    $session.f1FOV = Math.ceil(f1BaseFOV + f1Step * (horizontalFOVDegrees - f1BaseFOV) / f1Step);
}