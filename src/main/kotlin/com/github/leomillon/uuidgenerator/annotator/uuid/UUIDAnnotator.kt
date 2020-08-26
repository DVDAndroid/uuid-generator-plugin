package com.github.leomillon.uuidgenerator.annotator.uuid

import com.github.leomillon.uuidgenerator.parser.findUUIDs
import com.github.leomillon.uuidgenerator.parser.textRange
import com.github.leomillon.uuidgenerator.quickfix.uuid.UUIDRandomQuickFix
import com.github.leomillon.uuidgenerator.quickfix.uuid.UUIDReformatQuickFix
import com.github.leomillon.uuidgenerator.quickfix.uuid.UUIDToggleDashesQuickFix
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement

class UUIDDefaultAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        val rawText = (element as? LeafPsiElement)
            ?.text
            ?.takeIf { it.isNotBlank() }
            ?: return

        highlightInText(element, rawText, holder)
    }
}

private fun highlightInText(
    element: PsiElement,
    rawText: String,
    holder: AnnotationHolder
) {
    val startOffset = element.textRange.startOffset
    rawText.findUUIDs().forEach { matchResult ->
        val textRange = matchResult.textRange(startOffset)
        val matchingValue = matchResult.value
        holder.newAnnotation(HighlightSeverity.INFORMATION, "UUID")
            .range(textRange)
            .enforcedTextAttributes(DefaultLanguageHighlighterColors.CONSTANT.defaultAttributes)
            .withFix(UUIDRandomQuickFix(textRange))
            .withFix(UUIDReformatQuickFix(matchingValue, textRange))
            .withFix(UUIDToggleDashesQuickFix(matchingValue, textRange))
            .create()
    }
}
