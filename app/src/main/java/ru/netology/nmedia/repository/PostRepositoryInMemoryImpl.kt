package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Разочарование в SMM, влияние трендов и поиск новой профессии\n" +
                    "С 2000 по 2009 год я был владельцем магазина по продаже игровых приставок и игр. В то время начали активно развиваться социальные сети, и я видел в них перспективу для привлечения новых клиентов. Так я увлекся SMM и тренировался на собственном бизнесе. За полгода количество покупателей увеличилось на 130% только благодаря продвижению в социальных сетях, а я решил полностью уйти в SMM и проработал на фрилансе 8 лет.\n" +
                    "\n" +
                    "Но после этих 8 лет я снова задумался о смене профессии. На тот момент мне было уже 47. Во-первых, за это время не только само направление SMM стало дико популярным, но и обучение. Появилась масса курсов, на которых готовили SMM-специалистов за пару недель. Выпускники таких курсов для опыта соглашались работать за отзывы или совсем бесплатно. И на их фоне я был вынужден объяснять потенциальным заказчикам, почему они должны заплатить за работу именно мне, а не получить всё бесплатно у какого-то молодого специалиста. Даже мои постоянные клиенты начали отказываться от услуг в целях экономии.\n" +
                    "\n" +
                    "Во-вторых, меня напрягало, что у людей не было комплексного понимания, что такое SMM. Они считали, что SMM-специалист должен быть и копирайтером, и дизайнером, и таргетологом. Мне снова приходилось тратить время на объяснения, почему специалист «всё в одном» не может стоить 20 тысяч рублей в месяц. Это отнимало много нервов, и я начал искать новые возможности в интернет-профессиях.\n" +
                    "\n" +
                    "И вот я увидел,что намечается тенденция развития отрасли мобильных приложений. Я читал исследования рынка, где приводилась информация, что с каждым годом всё больше пользователей переходит с компьютеров на мобильные приложения. Начал мониторить рынок на предмет востребованности профессии и понял, что специалистов на рынке мало, а спрос большой. Также я увидел, что огромными темпами развивается индустрия мобильных игр, а в теме игр я работал с 2003 года, поэтому направление мне было близко.\n" +
                    "\n" +
                    "Сначала я начал изучать мобильный маркетинг самостоятельно, но направление было новым, поэтому информация мне попадалась довольно разрозненная. И тогда я посмотрел в сторону образовательных программ, которые дали бы систематизированную актуальную информацию, и взял курс «Маркетинг мобильных приложений» в Нетологии.\n" +
                    "\n",
            published = "20 в 10:12",
            likedByMe = false,
            countLikes = 1_999_999,
            countShares = 999
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Освоение новой профессии — это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни: менять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки → http://netolo.gy/fPD",
            published = "23 сентября в 10:12",
            likedByMe = false,
            countLikes = 1_999_999,
            countShares = 999,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            published = "22 сентября в 10:14",
            likedByMe = false,
            countLikes = 0,
            countShares = 0
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
            published = "22 сентября в 10:12",
            likedByMe = false,
            countLikes = 1_000,
            countShares = 10,
            video = "https://www.youtube.com/watch?v=1Rkn6rnsgc4"
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
            published = "21 сентября в 10:12",
            likedByMe = false,
            countLikes = 5_200,
            countShares = 1_100
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
            published = "20 сентября в 10:14",
            likedByMe = false,
            countLikes = 10,
            countShares = 1
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
            published = "19 сентября в 14:12",
            likedByMe = false,
            countLikes = 67,
            countShares = 5
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            published = "19 сентября в 10:24",
            likedByMe = false,
            countLikes = 2_100,
            countShares = 999
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
            published = "18 сентября в 10:12",
            likedByMe = false,
            countLikes = 1,
            countShares = 1
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            countLikes = 10_000_000,
            countShares = 99_999
        )
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else
                if (!it.likedByMe) it.copy(likedByMe = !it.likedByMe, countLikes = ++it.countLikes) else
                    it.copy(likedByMe = !it.likedByMe, countLikes = --it.countLikes)
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(countShares = it.countShares + 1)
        }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "New post with id: ${nextId++}"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun getPost(id: Long) : Post {
        return posts.single { it.id == id }
    }
}