package com.github.leomillon.uuidgenerator

import com.github.leomillon.uuidgenerator.settings.cuid.CUIDFormatSettings
import com.github.leomillon.uuidgenerator.settings.cuid.CUIDGeneratorSettings
import cool.graph.cuid.Cuid

object CUIDGenerator {

    fun generateCUID() = generateCUID(CUIDGeneratorSettings.instance)

    fun generateCUID(settings: CUIDFormatSettings): String =
        formatCUID(Cuid.createCuid(), settings)

    private fun formatCUID(id: String, generatorSettings: CUIDFormatSettings): String =
        id.let {
            if (generatorSettings.isLowerCased()) it.toLowerCase() else it.toUpperCase()
        }
}
