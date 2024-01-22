package dev.georgiys.unzipfileswithpass

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.exception.ZipException
import java.io.File


fun main() {
    print("Enter the name of the folder or its directory: ")
    val folder = readlnOrNull()
    val dir = File(folder ?: throw Exception("You cannot enter an empty string"))

    var password: String? = null

    val zipFileList: MutableList<File> = ArrayList()
    try {
        checkFolder(dir, zipFileList)
    } catch (ex: Exception){
        println(ex.message)
    }
    zipFileList.forEach {
        try {
            val zipFile = ZipFile(it)
            if (zipFile.isEncrypted) {
                if (password == null){
                    print("Enter the password for zipFile: ")
                    password = readlnOrNull()
                }
                zipFile.setPassword(password?.toCharArray())
            }
            val directoryZip = setFolder(it)
            println(directoryZip)
            try {
                zipFile.extractAll(directoryZip)
            } catch (exception: ZipException){
                print("This is are not correct pass, enter again: ")
                password = readlnOrNull()
                zipFile.setPassword(password?.toCharArray())
                zipFile.extractAll(directoryZip)
            }
        } catch (e: ZipException) {
            e.printStackTrace()
            println("fail ${e.message}")
        }
    }
}

fun checkFolder(source: File, zipFileList:MutableList<File>){
    source.listFiles()!!.forEach {
        if (!it.isFile){
            checkFolder(it, zipFileList)
        } else if (getTypeFile(it) == "zip"){
            zipFileList.add(it)
        }
    }
}

fun getTypeFile(file: File): String {
    var extension = ""
    val i: Int = file.name.lastIndexOf('.')
    if (i > 0) {
        extension = file.name.substring(i + 1)
    }
    return extension
}

fun setFolder(file: File): String {
    var extension = ""
    val i: Int = file.absolutePath.lastIndexOf('.')
    if (i > 0) {
        extension = file.absolutePath.substring(0, i)
    }
    return extension
}