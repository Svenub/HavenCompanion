package se.umu.svke0008.havencompanion.data.local.dao

import androidx.room.Dao

@Dao
interface ScenarioDao {




    /*
    @Transaction
    suspend fun insertScenarioWithGameCharacters(
        scenario: ScenarioEntity,
        gameCharacters: List<GameCharacterEntity>
    ) {
        val scenarioId = insertScenario(scenario)
        if (scenarioId != -1L) {
            val gameCharactersWithScenarioId =
                gameCharacters.map { it.copy(belongToScenarioId = scenarioId.toInt()) }
            insertGameCharacters(gameCharactersWithScenarioId)
        } else {
            // Handle scenario insertion failure
        }
    }

     */


/*
    @Query("SELECT * FROM scenario_table WHERE scenarioName = :scenarioName")
    suspend fun getScenarioByName(scenarioName: String): Scenario?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertScenario(scenario: Scenario)


 */
}