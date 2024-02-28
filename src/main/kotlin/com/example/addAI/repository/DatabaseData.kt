package com.example.addAI.repository

import org.springframework.stereotype.Repository

@Repository
class DatabaseData {

    fun jokes(): Array<String>{
        return arrayOf(
                "One plus one is really fun",
                "Chelsea",
                "Two go side by side and the middle one fell down",
                "hahahahaha, laugh is contaigous"
        )
    }

    fun questions(): Array<String>{
        return arrayOf(
                "Can you tell me a joke?",
                "Tell me a joke",
                "Convert <amount> <from> to <to>",
                "How much is <amount> <from> in <to>"
        )
    }

}