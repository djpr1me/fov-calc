require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: functions.js
theme: /

    state: Start
        q!: $regex</start>
        a: Привет. Это калькулятор угла поля зрения для автосимуляторов. Я задам тебе несколько вопросов, чтобы рассчитать правильное значение. Начнем. || htmlEnabled = false, html = "Привет. Это калькулятор угла поля зрения для автосимуляторов. Я задам тебе несколько вопросов, чтобы рассчитать правильное значение. Начнем."
        a: Давай определимся с твоим сетапом, ты используешь один монитор или triple screen?
        buttons:
            "Один монитор" -> /single-screen-ratio
            "Triple screen" -> /triple-screen

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

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
            calculateFOV($session.screenSize, $session.distance, $session.aspectRatio);
        a: Для твоего сетапа будет правильно установить:
            
            Горизонтальный FOV: {{$session.horizontalFOV}} градусов
            Вертикальный FOV: {{$session.verticalFOV}} градусов. || htmlEnabled = true, html = "Для твоего сетапа будет правильно установить:&nbsp;<br><ul><li>Горизонтальный FOV: {{$session.horizontalFOV}} градусов</li><li>Вертикальный FOV: {{$session.verticalFOV}} градусов.</li></ul>"

    state: single-screen-ratio
        a: Давай определимся с соотношением сторон, выбери из списка какое у тебя соотношение сторон у монитора.
        buttons:
            "4:3" -> /set-ratio-4-3
            "16:9" -> /set-ratio-16-9
            "21:9" -> /set-ratio-21-9
            "32:9" -> /set-ratio-32-9

    state: set-ratio-4-3
        script:
            $session.aspectRatio = 4 / 3;
        go!: /screen-size

    state: set-ratio-16-9
        script:
            $session.aspectRatio = 16 / 9;
        go!: /screen-size

    state: set-ratio-21-9
        script:
            $session.aspectRatio = 21 / 9;
        go!: /screen-size

    state: set-ratio-32-9
        script:
            $session.aspectRatio = 32 / 9;
        go!: /screen-size

    state: screen-size
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