package com.ayushsinha.aimentor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Topic(val name: String, val description: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AIMentorApp() }
    }
}

@Composable
fun AIMentorApp() {
    val topics = listOf(
        Topic("Java", "Core Java, JVM, collections, concurrency, Java 8–21"),
        Topic("Spring Boot", "Spring, REST, Security, transactions, internals"),
        Topic("DSA", "LeetCode-style problems, patterns and complexity"),
        Topic("System Design", "Scalability, APIs, databases, caching, messaging"),
        Topic("Kafka & Redis", "Distributed systems, messaging, caching and failure"),
        Topic("AI / ML", "LLMs, RAG, agents, embeddings and ML fundamentals"),
        Topic("Kubernetes", "Containers, deployments, services and production debugging"),
        Topic("Databases", "SQL, indexing, transactions and distributed databases")
    )

    var selected by remember { mutableStateOf<Topic?>(null) }
    var answer by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (selected == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text("AI Mentor", style = MaterialTheme.typography.headlineLarge)
                        Text(
                            "Your adaptive interview coach",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(12.dp))
                        Text("Choose a topic", style = MaterialTheme.typography.titleLarge)
                    }
                    items(topics) { topic ->
                        ElevatedCard(
                            onClick = { selected = topic },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(topic.name, style = MaterialTheme.typography.titleMedium)
                                Text(topic.description)
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp)
                ) {
                    Text(
                        "Interview: ${selected!!.name}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.height(16.dp))

                    Text(
                        questionFor(selected!!.name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = answer,
                        onValueChange = { answer = it },
                        modifier = Modifier.fillMaxWidth().height(180.dp),
                        placeholder = { Text("Explain your answer as if you're in an interview...") }
                    )

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            feedback = coachFeedback(selected!!.name, answer)
                        },
                        enabled = answer.isNotBlank()
                    ) {
                        Text("Evaluate Answer")
                    }

                    Spacer(Modifier.height(16.dp))

                    if (feedback.isNotBlank()) {
                        Card(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(16.dp)) {
                                Text("Coach feedback", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(8.dp))
                                Text(feedback)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    OutlinedButton(onClick = {
                        selected = null
                        answer = ""
                        feedback = ""
                    }) {
                        Text("Back to topics")
                    }
                }
            }
        }
    }
}

fun questionFor(topic: String): String = when (topic) {
    "Java" -> "Explain HashMap internals in Java and what happens during a collision."
    "Spring Boot" -> "How does a Spring Boot request travel from Tomcat to a controller?"
    "DSA" -> "Given an array, find the length of the longest subarray with sum K. Explain your approach."
    "System Design" -> "Design a highly scalable notification system for 10 million users."
    "Kafka & Redis" -> "Why would you choose Kafka over direct asynchronous service-to-service calls?"
    "AI / ML" -> "Explain RAG and why it can be useful for an interview-coaching agent."
    "Kubernetes" -> "A pod keeps restarting in production. How would you investigate it?"
    "Databases" -> "Explain database indexing and when an index can actually hurt performance."
    else -> "Explain the topic and discuss important production trade-offs."
}

fun coachFeedback(topic: String, answer: String): String {
    val length = answer.trim().split(Regex("\\s+")).size
    return buildString {
        if (length < 25) {
            append("Your answer is too brief for a technical interview. ")
            append("Explain the mechanism, give an example, and discuss at least one trade-off. ")
        } else {
            append("Good starting depth. ")
            append("In a real interview, make the answer more structured: definition → internal working → example → trade-offs. ")
        }
        append("Next, expect a follow-up question that tests the internal details of $topic.")
    }
}
