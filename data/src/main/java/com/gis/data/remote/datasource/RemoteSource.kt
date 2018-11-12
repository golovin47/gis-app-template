package com.gis.data.remote.datasource

import com.gis.data.remote.entity.PeopleR
import com.gis.domain.datasource.DataSource
import com.gis.domain.entity.People
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class RemoteSource : DataSource {

    override fun getPeople(): Observable<List<People>> {
        val people = mutableListOf<PeopleR>()
        for (i in 0..100) {
            people.add(
                PeopleR(
                    id = i,
                    name = "John #$i",
                    metAt = "09.09.200$i",
                    contact = "Contact $i",
                    email = "john$i@gmail.com",
                    facebook = "John${i}FB",
                    twitter = "John${i}Twitter"
                )
            )
        }

        return Observable.just(people.map { it.toDomain() }.toList())
            .delay(3000, TimeUnit.MILLISECONDS)
    }

    override fun findByName(name:String): Observable<List<People>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertPeople(people: List<People>): Completable =
        Completable.fromAction { val i = 0 }

    private fun PeopleR.toDomain() =
        People(
            id = id,
            name = name,
            metAt = metAt,
            contact = contact,
            email = email,
            facebook = facebook,
            twitter = twitter
        )
}