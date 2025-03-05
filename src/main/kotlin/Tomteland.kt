class Tomteland {
    val tomtar = mapOf(  "Tomten" to mutableListOf("Glader", "Butter"),
        "Glader" to mutableListOf("Tröger", "Trötter", "Blyger"),
        "Butter" to mutableListOf("Rådjuret", "Nyckelpigan", "Haren", "Räven"),
        "Räven" to mutableListOf("Gråsuggan", "Myran"),
        "Trötter" to mutableListOf("Skumtomten"),
        "Skumtomten" to mutableListOf("Dammråttan"),
        "Myran" to mutableListOf("Bladlusen"))

    fun getUnderlings(nisse: String, underlings: MutableList<String>) : List<String> {
        val underhuggare = tomtar[nisse] ?: return underlings

        if (underhuggare.isEmpty()) {
            return underlings
        }

        for (n in tomtar[nisse]!!) {
            underlings.add(n)
            getUnderlings(n, underlings)
        }
        return underlings
    }
}

fun main() {
    val tl = Tomteland()
    println("Under Tomten: ${tl.getUnderlings("Tomten", mutableListOf()).size}st ${tl.getUnderlings("Tomten", mutableListOf())}")
    println("Under Glader: ${tl.getUnderlings("Glader", mutableListOf()).size}st ${tl.getUnderlings("Glader", mutableListOf())}")
    println("Under Räven: ${tl.getUnderlings("Räven", mutableListOf()).size}st ${tl.getUnderlings("Räven", mutableListOf())}")
    println("Under Bladlusen: ${tl.getUnderlings("Bladlusen", mutableListOf()).size}st ${tl.getUnderlings("Bladlusen", mutableListOf())}")
}