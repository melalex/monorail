package com.melalex.monorail.error.dto

import com.melalex.monorail.error.model.Problem
import io.scalaland.chimney.Transformer

trait ProblemMappingSupport {

  implicit val problemTransformer: Transformer[Problem, ProblemDto] = Transformer.define[Problem, ProblemDto]
    .withFieldComputed(_.tittle, _.tittleKey.map())

}
