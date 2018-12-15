package com.gis.repoimpl.domain.interactors

import com.gis.repoimpl.domain.entitiy.User
import com.gis.repoimpl.domain.repository.UserRepository

class ObserveUserUseCase(private val userRepository: UserRepository) {

  fun execute() = userRepository.observeUser()
}


class SaveUserUseCase(private val userRepository: UserRepository) {

  fun execute(user: User) = userRepository.saveUser(user)
}


class RemoveUserUseCase(private val userRepository: UserRepository) {

  fun execute() = userRepository.removeUser()
}