require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: functions.js
theme: /

    state: Start
        q!: $regex</start>
        a: Привет. Это калькулятор угла поля зрения для автосимуляторов. Я задам тебе несколько вопросов, чтобы рассчитать правильное значение. Начнем. || htmlEnabled = false, html = "Привет. Это калькулятор угла поля зрения для автосимуляторов. Я задам тебе несколько вопросов, чтобы рассчитать правильное значение. Начнем."
        go!: /setup

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}
        buttons:
            "Начать сначала" -> /Start

    state: triple-screen
        a: В разработке... возвращайтесь позже
        buttons:
            "Назад" -> /setup

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
        go!: /pick-sim

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

    state: pick-sim
        a: Я все посчитал, выбери свой симулятор для настройки FOV.
        buttons:
            "Project Cars 1, 2" -> /hFOV
            "Richard Burns Rally" -> /hFOVRBR
            "F1 2021+" -> /hFOVF1
            "Assetto Corsa, Assetto Corsa Competizione" -> /vFOV
            "rFactor 1, 2" -> /vFOV
            "Dirt Rally 1, 2" -> /vFOVDirt

    state: hFOV
        a: Для твоего сетапа будет правильно установить FOV: {{$session.horizontalFOV}} градусов.
        buttons:
            "Назад" -> /pick-sim

    state: vFOV
        a: Для твоего сетапа будет правильно установить FOV: {{$session.verticalFOV}} градусов.
        buttons:
            "Назад" -> /pick-sim

    state: hFOVRBR
        a: Для твоего сетапа будет правильно установить FOV: {{$session.horizontalFOVRadians}} градусов.
        buttons:
            "Назад" -> /pick-sim

    state: vFOVDirt
        a: Для твоего сетапа будет правильно установить FOV: {{$session.dirtRallyFOV}} градусов.
        buttons:
            "Назад" -> /pick-sim

    state: hFOVF1
        a: Для твоего сетапа будет правильно установить FOV: {{$session.f1FOV}} градусов.
        buttons:
            "Назад" -> /pick-sim

    state: setup
        a: Давай определимся с твоим сетапом, ты используешь один монитор или triple screen?
        buttons:
            "Один монитор" -> /single-screen-ratio
            "Triple screen" -> /triple-screen