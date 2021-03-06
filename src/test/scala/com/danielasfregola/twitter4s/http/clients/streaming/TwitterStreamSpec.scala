package com.danielasfregola.twitter4s.http.clients.streaming

import akka.http.scaladsl.model.HttpRequest
import akka.stream.KillSwitch
import com.danielasfregola.twitter4s.util.streaming.ClientSpec
import org.specs2.mock.Mockito

class TwitterStreamSpec extends ClientSpec with Mockito {

  class TwitterStreamSpecContext extends ClientSpecContext {
      val switch = mock[KillSwitch]
      val request = HttpRequest()
      doNothing when switch shutdown
      val stream = new TwitterStream(switch, request)(consumerToken, accessToken, system)
  }

  "Twitter Stream" should {

    "be closable" in new TwitterStreamSpecContext {
      stream.close
      there was one(switch).shutdown
    }

    "close previous stream when siteEvents" in new TwitterStreamSpecContext {
      stream.siteEvents()(dummyProcessing)
      there was one(switch).shutdown
    }

    "close previous stream when userEvents" in new TwitterStreamSpecContext {
      stream.userEvents()(dummyProcessing)
      there was one(switch).shutdown
    }

    "close previous stream when filterStatuses" in new TwitterStreamSpecContext {
      stream.filterStatuses(track = Seq("Whatever"))(dummyProcessing)
      there was one(switch).shutdown
    }

    "close previous stream when sampleStatuses" in new TwitterStreamSpecContext {
      stream.sampleStatuses()(dummyProcessing)
      there was one(switch).shutdown
    }

    "close previous stream when firehoseStatuses" in new TwitterStreamSpecContext {
      stream.firehoseStatuses()(dummyProcessing)
      there was one(switch).shutdown
    }

  }

}
