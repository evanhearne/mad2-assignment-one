package ie.setu.mad2_assignment_one.inventory

import android.app.Application
import ie.setu.mad2_assignment_one.data.AppContainer
import ie.setu.mad2_assignment_one.data.AppDataContainer

class InventoryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}