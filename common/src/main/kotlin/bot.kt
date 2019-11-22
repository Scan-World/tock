/*
* Copyright (C) 2017/2019 VSCT
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ai.tock.demo.common

import ai.tock.bot.api.client.newBot
import ai.tock.bot.api.client.newStory
import ai.tock.bot.api.client.unknownStory
import ai.tock.bot.api.model.context.Entity
import ai.tock.bot.api.model.message.bot.Card
import ai.tock.bot.connector.web.webButton
import ai.tock.bot.connector.web.webMessage
import ai.tock.bot.definition.Intent
import ai.tock.demo.common.models.ResponsePost
import ai.tock.demo.common.models.ResponsePostGoogle
import ai.tock.nlp.api.client.model.EntityType
import ai.tock.nlp.entity.NumberValue
import ai.tock.shared.property
import java.net.URL

val apiKey = property("tock_bot_api_key", "b98995e4-9d06-4ba5-84de-3b560e8eecb9")

val bot = newBot(
        "b98995e4-9d06-4ba5-84de-3b560e8eecb9",

        newStory("location") {
            val tmp_location = entityText("location")
            send("Cool !")
            send("Que voulez vous y faire ?")
            end(
                    newCarousel(
                            listOf(
                                    newCard(
                                            "Bar",
                                            null,
                                            newAttachment("https://webfiles4.luxweb.com/upload/cms/213/large/19239afa-2ac7-4dd3-a930-1992b3026cb8.jpg"),
                                            newAction("Bar")
                                    ),
                                    newCard(
                                            "Restaurant",
                                            null,
                                            newAttachment("https://cdn.futura-sciences.com/buildsv6/images/wide1920/f/5/d/f5de4237bc_103161_restaurant-hommes-femmes-fotolia.jpg"),
                                            newAction("Restaurant")
                                    ),
                                    newCard(
                                            "Musée",
                                            null,
                                            newAttachment("https://images.unsplash.com/photo-1496889196885-5ddcec5eef4d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"),
                                            newAction("Musée")
                                    ),
                                    newCard(
                                            "Sport",
                                            null,
                                            newAttachment("http://resize-football.ladmedia.fr/r/920,/crop/920,512/img/images/media/images/white-hart-lane-tottenham/13882200-1-fre-FR/White-Hart-Lane-Tottenham.jpg"),
                                            newAction("Sport")
                                    ),
                                    newCard(
                                            "Autre",
                                            null,
                                            newAttachment("https://img.bfmtv.com/c/1000/600/84b/ed81162fe497c4e37128d2cdce1cc.jpeg"),
                                            newAction("Autre")
                                    )
                            )
                    )
            )
        },

        newStory("ask_for_proposal") {
            //cleanup entities
            val tmp_location = entityText("location")
            val tmp_type = entityText("event_type")
            //entities.add(Entity("custom", "event_type",null, value= NumberValue(2), new = true))
            //entityValue<NumberValue>("event_type")?.value

            val tmp_url = "http://6a248fc8.ngrok.io/posts_by_cat/" + tmp_location + "/" + tmp_type
            val result = URL(tmp_url).readText()
            val posts = ResponsePostGoogle(result)

           // entities.clear()

            send("Super, voici notre sélection personnalisée !")


            val cards: List<Card>? = posts.data?.map {
                newCard(
                        it.name,
                        null,
                        it.url?.let { it1 -> newAttachment(it1) },
                        newAction("Maps", it.url)
                )
            }
            if (cards != null) {
                end(newCarousel(cards))
            } else {
                end("error")
            }
        },


        unknownStory {
            end {
                //custom model sample
                webMessage(
                        "Sorry - not understood",
                        webButton("Card", Intent("card")),
                        webButton("Carousel", Intent("carousel"))
                )
            }
        })
