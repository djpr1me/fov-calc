require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Привет. Это калькулятор угла поля зрения для автосимуляторов. Я задам тебе несколько вопросов, чтобы рассчитать правильное значение. Начнем. || htmlEnabled = false, html = "Привет. Это калькулятор угла поля зрения для автосимуляторов. Я задам тебе несколько вопросов, чтобы рассчитать правильное значение. Начнем."
        a: Давай определимся с твоим сетапом, ты используешь один монитор или triple screen?
        buttons:
            "Один монитор" -> /single-screen
            "Triple screen" -> /triple-screen

    state: Hello
        intent!: /привет
        a: Привет привет

    state: Bye
        intent!: /пока
        a: Пока пока

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

    state: Match
        event!: match
        a: {{$context.intent.answer}}

    state: single-screen
        a: Отлично, возьми рулетку и измеряй диагональ видимой области экрана (не включая безель).
        InputNumber: 
            prompt = Введи результат в сантиметрах.
            varName = screenSize
            html = 
            htmlEnabled = false
            failureMessage = ["Укажи правильный размер в диапазоне от 48 до 124 сантиметров."]
            failureMessageHtml = [""]
            then = /distance
            minValue = 48
            maxValue = 124
            actions = 

    state: triple-screen
        a: В разработке... возвращайтесь позже
        buttons:
            "Назад" -> /Start

    state: distance
        a: Супер, а теперь сядь в кресло в рабочую позицию и измеряй расстояние от глаз до монитора.
        InputNumber: 
            prompt = Введи результат в сантиметрах.
            varName = distance
            html = 
            htmlEnabled = false
            failureMessage = ["Упс, проверь еще раз свои измерения, обычно для маленьких мониторов расстояние до экрана не должно быть меньше 50 см (макс 150 см).."]
            failureMessageHtml = [""]
            then = /calc
            minValue = 50
            maxValue = 150
            actions = 

    state: calc
        script:
            function calculateFOV(screenDiagonal, distanceToScreen) {
            const aspectRatio = 16 / 9;
            const screenHeight = Math.sqrt(Math.pow(screenDiagonal, 2) / (Math.pow(aspectRatio, 2) + 1));
            const screenWidth = screenHeight * aspectRatio;
            const horizontalFOV = 2 * Math.atan((screenWidth / 2) / distanceToScreen) * (180 / Math.PI);
            const verticalFOV = 2 * Math.atan((screenHeight / 2) / distanceToScreen) * (180 / Math.PI);
            return {
                horizontalFOV: horizontalFOV.toFixed(2),
                verticalFOV: verticalFOV.toFixed(2)
            };
                }
                // Использование переменных из $session
                const screenDiagonal = $session.screenSize; // диагональ экрана в сантиметрах
                const distanceToScreen = $session.distance; // расстояние до экрана в сантиметрах
                const fov = calculateFOV(screenDiagonal, distanceToScreen);
        a: Горизонтальный FOV: ${fov.horizontalFOV} градусов