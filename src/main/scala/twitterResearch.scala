import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token
import twitter4j._
import twitter4j.conf._
import scala.collection.JavaConversions._



/**
 * Girls Senseのつぶやきを形態素解析し、どのワードを含むとリツイートされやすいか統計を取る
 */
object TwitterResearch {

  /**
   * Girls Senseの公式リツイートされた記事を取得
   * @param userName String
   * @return ResponseList[Status]
   */
  def getTwitter(userName: String): ResponseList[Status] = {
    val cb = new ConfigurationBuilder
    // GSのAPIキー
    cb.setOAuthConsumerKey("dC9AyyxFfvuE2hrLTmKo6jK42")
      .setOAuthConsumerSecret("TdpOfIuUy9XxODzml0v0BGgascymxXS33MrG4EJL1eIP4jYnbu")
      .setOAuthAccessToken("2179238514-WmsFHyywLResYXYcOHBuMCb5L9t0yohw31sszFI")
      .setOAuthAccessTokenSecret("W4tVJ1p3uIOvE6fkCEDdeNvdjk1sXSq838tlaTIErX1Oo")

    val twitterFactory = new TwitterFactory(cb.build)
    val tt = twitterFactory.getInstance

    val timeLine = tt.timelines
    timeLine.getRetweetsOfMe
  }

  /**
   * つぶやきを形態素解析してサマる
   * @param gsRetweets ResponseList[Status]
   */
  def tweetAnalysis(gsRetweets: ResponseList[Status]) = {

    val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build

    val tokens = for (rl <- gsRetweets) yield tokenizer.tokenize(rl.getText)

    tokens.foreach { x =>
      val sorted = x.filter(x => x.getPartOfSpeech.startsWith("名詞")).groupBy(x => x.getBaseForm).values.toList.sortWith(_.length>_.length)
//      println(sorted)
      sorted.foreach { x =>
        x.filter(x => x.getPartOfSpeech.startsWith("名詞")).groupBy(x => x.getBaseForm).values.toList.sortWith(_.length>_.length)
      }
    }

//    for (rl <- gsRetweets) {
//
//      val tokens = tokenizer.tokenize(rl.getText)
//
//      val sorted = tokens.filter(x => x.getPartOfSpeech.startsWith("名詞")).groupBy(x => x.getBaseForm).values.toList.sortWith(_.length>_.length)
//
//      sorted.foreach { x =>
//        println("count: " +x.length+" "+x(0).getBaseForm())
//      }
//    }


//      tokens.foreach { t =>
//        val token = t.asInstanceOf[Token]
//        println(s"${token.getSurfaceForm} - ${token.getAllFeatures}")
//      }

  }

  /**
   * 実行
   */
  def main (args: Array[String]) {

    val userName = "Girls_Sense"
    val gsRetweets = getTwitter(userName)
    tweetAnalysis(gsRetweets)
//    println(gsRetweets)
//    tweetAnalysis(gsRetweets)
//    tweetAnalysis()
  }
}
