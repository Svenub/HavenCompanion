package se.umu.svke0008.havencompanion.domain.repository

import kotlinx.coroutines.flow.Flow
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.scenario.Scenario

interface ScenarioRepository {

    suspend fun addScenario(scenario: Scenario)

    suspend fun addScenarioWithCharacters(scenario: Scenario, characters: List<GameCharacter>)

    fun getAllScenarios(): Flow<List<Scenario>>

    suspend fun getScenarioById(scenarioId: Int): Flow<Scenario?>

    suspend fun deleteScenario(scenarioName: Scenario)

/*
    suspend fun insertScenario(scenario: Scenario)



 */

}