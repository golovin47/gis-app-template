package com.gis.domain.usecase

import io.reactivex.Observable

interface UseCase<Request, Response> {
    fun execute(request: Request): Response
}