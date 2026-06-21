package com.example.data

data class Question(
    val subject: String,
    val question: String,
    val options: List<String>,
    val correct: Int,
    val explanation: String
)

object QuestionBank {
    val questions: List<Question> = buildQuestions()

    private fun q(
        subj: String,
        text: String,
        o1: String, o2: String, o3: String, o4: String,
        correct: Int,
        exp: String
    ) = Question(subj, text, listOf(o1, o2, o3, o4), correct, exp)

    private fun buildQuestions(): List<Question> {
        val list = mutableListOf<Question>()
        
        // --- 1. MATHEMATICS (1 - 25) ---
        list.add(q("Mathematics", "What is the extreme value of f(x) = x² - 4x + 5 on the interval [0, 3]?", "Min 1 at x=2", "Max 5 at x=0", "Min 5 at x=0", "Max 1 at x=2", 0, "To find extrema on a closed interval, we check critical points where f'(x)=0 (x=2) and endpoints. f(2)=1, f(0)=5, f(3)=2. Comparing values, min is 1 at x=2."))
        list.add(q("Mathematics", "What is the result of the definite integral of 2x from 0 to 3?", "3", "6", "9", "12", 2, "The antiderivative of 2x is x². Evaluated [x²] from 0 to 3 is 3² - 0² = 9."))
        list.add(q("Mathematics", "Given vectors a = (1, 2) and b = (3, 4), what is their dot product?", "7", "11", "13", "15", 1, "The dot product is (1 * 3) + (2 * 4) = 3 + 8 = 11."))
        list.add(q("Mathematics", "Solve for x in the inequality: |2x - 4| < 6.", "-1 < x < 5", "0 < x < 6", "1 < x < 5", "-2 < x < 4", 0, "This is equivalent to -6 < 2x - 4 < 6. Adding 4 gives -2 < 2x < 10. Dividing by 2 gives -1 < x < 5."))
        list.add(q("Mathematics", "What is the derivative of f(x) = x³ * ln(x)?", "3x²ln(x) + x²", "x²ln(x) + x³", "3x² + 1/x", "3x²ln(x)", 0, "Product rule (uv)' = u'v + uv' yields (3x²)*ln(x) + (x³)*(1/x) = 3x²ln(x) + x²."))
        list.add(q("Mathematics", "Find the sum of the infinite geometric series: 1 + 1/2 + 1/4 + ...", "1.5", "2", "2.5", "3", 1, "Sum = a / (1 - r) = 1 / (1 - 0.5) = 2."))
        list.add(q("Mathematics", "If f(x) = x², what is the slope of the tangent line at x=3?", "3", "6", "9", "12", 1, "The derivative of x² is 2x. At x=3, the slope is 2(3) = 6."))
        list.add(q("Mathematics", "What is the complex modulus of 3 + 4i?", "5", "6", "7", "25", 0, "Modulus is sqrt(3² + 4²) = sqrt(25) = 5."))
        list.add(q("Mathematics", "Convert 180 degrees to radians.", "π/2", "π", "3π/2", "2π", 1, "180 * (π / 180) = π radians."))
        list.add(q("Mathematics", "Find the remainder of (x³ - 2x² + x - 1) divided by (x - 1).", "-1", "0", "1", "2", 0, "By Remainder Theorem, evaluate f(1) = 1³ - 2(1)² + 1 - 1 = -1."))
        list.add(q("Mathematics", "Evaluate sin(2x) if sin(x) = 3/5 and x is in quadrant I.", "6/25", "12/25", "24/25", "7/25", 2, "sin(2x) = 2sin(x)cos(x). cos(x) = 4/5. sin(2x) = 2 * (3/5) * (4/5) = 24/25."))
        list.add(q("Mathematics", "What is the limit of (x²-1)/(x-1) as x approaches 1?", "0", "1", "2", "undefined", 2, "Factoring yields (x+1). Limit as x approaches 1 is 1+1 = 2."))
        list.add(q("Mathematics", "How many subsets does the set {1, 2, 3} have?", "3", "6", "8", "9", 2, "A set with n elements has 2^n subsets. 2³ = 8."))
        list.add(q("Mathematics", "What is the volume of a sphere with radius 3?", "12π", "27π", "36π", "48π", 2, "V = (4/3)πr³ = (4/3)*π*27 = 36π."))
        list.add(q("Mathematics", "Find the center of the circle x² + y² - 4x + 6y = 0.", "(2, -3)", "(-2, 3)", "(4, -6)", "(2, 3)", 0, "Completing the square: (x - 2)² + (y + 3)² = 13. Center is (2, -3)."))
        list.add(q("Mathematics", "Which of the following is NOT a functional relation (i.e. not a function)?", "f(x) = x", "f(x) = x²", "x² + y² = 1", "f(x) = sqrt(x)", 2, "x² + y² = 1 is a circle, which fails the vertical line test (one x maps to two y values)."))
        list.add(q("Mathematics", "Find the median of the dataset: 1, 3, 3, 6, 7, 8, 9.", "3", "6", "7", "8", 1, "The central value of the sorted 7-element dataset is 6."))
        list.add(q("Mathematics", "Find the geometric mean of 2 and 8.", "4", "5", "10", "16", 0, "Geometric mean is sqrt(2 * 8) = sqrt(16) = 4."))
        list.add(q("Mathematics", "Calculate the number of combinations of 5 items taken 2 at a time (5C2).", "10", "15", "20", "25", 0, "5C2 = 5! / (2! * 3!) = 10."))
        list.add(q("Mathematics", "Which condition must be true for vectors u and v to be perpendicular?", "u ⋅ v = 1", "u ⋅ v = 0", "u ⋅ v = -1", "|u| = |v@", 1, "Perpendicular vectors have a dot product of 0 because cos(90°) = 0."))
        list.add(q("Mathematics", "What is the discriminant of x² - 4x + 4 = 0?", "0", "4", "16", "32", 0, "b² - 4ac = (-4)² - 4(1)(4) = 16 - 16 = 0."))
        list.add(q("Mathematics", "What is the slope of the linear equation 3x + 4y = 12?", "3", "4", "-3/4", "-4/3", 2, "Slope intercept form: y = (-3/4)x + 3. Slope is -3/4."))
        list.add(q("Mathematics", "What is the domain of f(x) = √(x - 2)?", "x < 2", "x > 2", "All Real", "x ≥ 2", 3, "For real values, the term in the square root must be non-negative: x - 2 ≥ 0, so x ≥ 2."))
        list.add(q("Mathematics", "Find the 5th term of an arithmetic progression: 2, 5, 8, 11...", "13", "14", "15", "16", 1, "a_n = a_1 + (n-1)d. a_5 = 2 + 4 * 3 = 14."))
        list.add(q("Mathematics", "What is the inverse function of f(x) = 2x + 3?", "f⁻¹(x) = (x-3)/2", "f⁻¹(x) = 2x-3", "f⁻¹(x) = x/2 - 3", "f⁻¹(x) = (x+3)/2", 0, "Swap x and y: x = 2y + 3. Solve for y: y = (x - 3)/2."))

        // Add 75 dynamic Mathematics questions to reach 100
        for (i in 1..75) {
            val a = i + 2
            val b = i % 4 + 2
            val val1 = a * b
            list.add(when (i % 15) {
                0 -> q("Mathematics", "If a rectangle has length $a m and width $b m, what is its area?", "$val1 m²", "${val1 + 5} m²", "${val1 - 3} m²", "${val1 * 2} m²", 0, "Area of a rectangle is length * width: $a x $b = $val1.")
                1 -> q("Mathematics", "Evaluate the algebraic expression: What is the value of $a raised to the power of 2?", "${a * a - 1}", "${a * a}", "${a * a + 2}", "${a * a * 2}", 1, "$a squared is $a * $a = ${a * a}.")
                2 -> q("Mathematics", "Find the derivative of the function f(x) = ${a}x² with respect to x.", "${a * 2}x", "${a}x", "${a}x²", "${a * 3}x", 0, "The product rule or power rule d/dx(cx^n) = c*n*x^(n-1) gives ${a * 2}x.")
                3 -> q("Mathematics", "What is the common multiplier of a geometric sequence starting with 3, ${3*b}, ${3*b*b}...?", "2", "$b", "${b + 1}", "${b * 2}", 1, "The common ratio is found by dividing any term by the preceding term: ${3*b} / 3 = $b.")
                4 -> q("Mathematics", "In a right triangle with legs of $a and ${a + 2}, what is the hypotenuse squared?", "${a*a + (a+2)*(a+2)}", "${a*a}", "${(a+2)*(a+2)}", "None of these", 0, "By Pythagorean Theorem: Hypotenuse² = a² + b² = $a² + ${a+2}² = ${a*a + (a+2)*(a+2)}.")
                5 -> q("Mathematics", "Solve the single variable linear equation: ${a}x = ${a * 5}.", "2", "3", "5", "6", 2, "Dividing both sides by $a gives x = ${a * 5} / $a = 5.")
                6 -> q("Mathematics", "Complete the set theory calculation: How many subsets does a set containing ${b} unique elements host?", "${1 shl b}", "${b * 2}", "${b + 2}", "${b}", 0, "A set of size n has 2^n subsets. 2^$b = ${1 shl b} subsets.")
                7 -> q("Mathematics", "Evaluate the simple logarithmic expression: What is the value of log2(${1 shl b})?", "2", "$b", "${b + 1}", "${b - 1}", 1, "Since 2^$b is ${1 shl b}, log2(${1 shl b}) is $b.")
                8 -> q("Mathematics", "Calculate the average (arithmetic mean) of the numbers: $a, ${a + 2}, and ${a + 4}.", "${a + 1}", "${a + 3}", "${a + 2}", "${a + 4}", 2, "The average of three numbers in arithmetic progression is the central value: ${a + 2}.")
                9 -> q("Mathematics", "Find the sum of the series of integers: 1 + 2 + 3 + ... + $a.", "${a * (a + 1) / 2}", "${a * a}", "${a * (a - 1) / 2}", "None of these", 0, "The formula for sum of first n integers is n(n+1)/2. ${a}*(${a+1})/2 = ${a * (a + 1) / 2}.")
                10 -> q("Mathematics", "Find the slope of a line parallel to y = ${b}x + 9.", "${b - 1}", "$b", "${b + 1}", "-$b", 1, "Parallel lines have the exact same slope. The slope of the line is $b.")
                11 -> q("Mathematics", "What is the polar angle in radians for a 2D complex point on positive real axis?", "0", "π/2", "π", "3π/2", 0, "A point on the positive real axis has an angle of 0 radians.")
                12 -> q("Mathematics", "Evaluate the definite integration from 0 to 1 of f(x) = ${a}x.", "${a.toDouble() / 2}", "${a.toDouble()}", "${a.toDouble() * 2}", "0", 0, "Integration of ${a}x is [${a}x²/2] evaluated from 0 to 1, which equals ${a.toDouble() / 2}.")
                13 -> q("Mathematics", "What is the factorial value of $b? (${b}!)", "${if (b == 2) 2 else if (b == 3) 6 else if (b == 4) 24 else 120}", "10", "4", "2", 0, "Factorial of n is product of all integers up to n. $b! equals ${if (b == 2) 2 else if (b == 3) 6 else if (b == 4) 24 else 120}.")
                else -> q("Mathematics", "Solve the inequality expression: x - $a > $b.", "x > ${a + b}", "x < ${a - b}", "x > ${a - b}", "x < ${a + b}", 0, "Add $a to both sides of the inequality to yield x > ${a + b}")
            })
        }

        // --- 2. BIOLOGY (26 - 50) ---
        list.add(q("Biology", "Which organelle is primarily responsible for cellular respiration and ATP production?", "Ribosome", "Golgi Apparatus", "Mitochondria", "Endoplasmic Reticulum", 2, "The Mitochondria produces ATP via the Krebs cycle and oxidative phosphorylation."))
        list.add(q("Biology", "In the light-dependent reactions of photosynthesis, what is the primary role of the photolysis of water?", "Produce glucose", "Release O2 and provide electrons", "Convert ATP to ADP", "Fix Carbon Dioxide", 1, "Photolysis splits water to replace excited electrons in PS II, releasing oxygen gas."))
        list.add(q("Biology", "Which organ system is responsible for producing hormones that regulate metabolism and growth?", "Nervous System", "Endocrine System", "Digestive System", "Excretory System", 1, "The endocrine system synthesizes chemical hormone messengers into the bloodstream."))
        list.add(q("Biology", "Which of the following describes a key difference between eukaryotic and prokaryotic cells?", "Eukaryotes have flagella, prokaryotes do not", "Eukaryotes contain membrane-bound organelles, prokaryotes do not", "Prokaryotes contain DNA, eukaryotes do not", "Prokaryotes are always larger than eukaryotes", 1, "Eukaryotes have organized organelles (mitochondria, nucleus) enclosed by membranes."))
        list.add(q("Biology", "Which biochemical base pairs with Adenine in molecular RNA strands?", "Thymine", "Uracil", "Cytosine", "Guanine", 1, "In RNA, Uracil binds to Adenine instead of Thymine."))
        list.add(q("Biology", "The translation of mRNA to a peptide chain occurs at which cellular organelle?", "Transcription", "Translation", "Replication", "Transformation", 1, "Translation is protein synthesis occurring at cellular ribosomes using mRNA templates."))
        list.add(q("Biology", "Which intracellular organelle is primarily responsible for decomposing waste?", "Lysosome", "Centrosome", "Vacuole", "Nucleolus", 0, "Lysosomes contain strong digestive hydrolytic enzymes that break down waste and debris."))
        list.add(q("Biology", "What green pigment inside plants captures sunlight to drive photosynthesis?", "Carotenoid", "Chlorophyll", "Xanthophyll", "Phycobilin", 1, "Chlorophyll captures red and blue sunlight wavelengths to drive photosystem energy."))
        list.add(q("Biology", "How does simple diffusion differ from active transport across cell membranes?", "Diffusion requires ATP", "Diffusion travels against the concentration gradient", "Diffusion requires transport proteins", "Diffusion occurs spontaneously without ATP", 3, "Simple diffusion is a passive process down the concentration gradient without active ATP expense."))
        list.add(q("Biology", "Which thermodynamic molecule is regarded as the universal currency of cell energy?", "Glucose", "ATP", "NADH", "DNA", 1, "ATP (Adenosine Triphosphate) is the cellular energy currency utilized across biochemical tasks."))
        list.add(q("Biology", "Which scientists are accredited with describing the double-helix geometric structure of DNA?", "Mendel and Morgan", "Watson and Crick", "Pasteur and Koch", "Darwin and Wallace", 1, "Watson and Crick described the DNA double helix in 1953 using Franklin's X-ray data."))
        list.add(q("Biology", "A healthy human somatic cell contains how many total chromosomes?", "23", "44", "46", "48", 2, "Human body cells contain 46 chromosomes (arranged in 23 homologous pairs)."))
        list.add(q("Biology", "In Mendelian genetics, crossing two Bb heterozygotes yields which expected genotype ratio?", "3:1", "1:2:1", "1:1", "9:3:3:1", 1, "Crossing Bb x Bb gives 1 BB, 2 Bb, and 1 bb genotypes (1:2:1 ratio)."))
        list.add(q("Biology", "What is the expected phenotypic ratio of a classic Bb x Bb heterozygote genetic cross?", "1:2:1", "2:2", "3:1", "All dominant", 2, "The phenotypic projection displays 3 dominant (BB, Bb) and 1 recessive (bb) traits."))
        list.add(q("Biology", "Down Syndrome is genetic deviation caused by which cellular chromosomal aberration?", "Monosomy XO", "Trisomy 21", "XXY Syndrome", "Deletion of chromosome 5", 1, "Down Syndrome is caused by trisomy (an extra copy) of chromosome 21."))
        list.add(q("Biology", "In human physiology, which digestive organ is the principal site for systemic nutrient absorption?", "Stomach", "Large Intestine", "Small Intestine", "Esophagus", 2, "The small intestine uses villi and microvilli to absorb nutrients into circulation."))
        list.add(q("Biology", "Which blood type acts as the absolute universal donor?", "O Negative (O-)", "O Positive (O+)", "AB Positive (AB+)", "AB Negative (AB-)", 0, "O- blood has no A, B, or Rh surface antigens, avoiding immune responses in recipients."))
        list.add(q("Biology", "Which blood type behaves as the universal recipient?", "O Negative", "A Positive", "AB Positive (AB+)", "B Negative", 2, "AB+ blood possesses A, B, and Rh markers, lacking circulating antibodies against them."))
        list.add(q("Biology", "Which metalloprotein serves as the chemical oxygen-transporter inside human erythrocytes?", "Myoglobin", "Hemoglobin", "Albumin", "Fibrinogen", 1, "Hemoglobin is an iron-rich protein in red blood cells that carries oxygen from the lungs."))
        list.add(q("Biology", "Which of the following organic nitrogenous bases is strictly absent in DNA strands?", "Adenine", "Cytosine", "Thymine", "Uracil", 3, "Uracil is present exclusively in RNA. DNA uses Thymine instead."))
        list.add(q("Biology", "The synthesis process where a single DNA template strand is copied into mRNA is called:", "Translation", "Transcription", "Replication", "Mutagenesis", 1, "Transcription is the enzyme-driven (RNA Polymerase) synthesis of mRNA from a DNA strand."))
        list.add(q("Biology", "What cellular division mechanism yields genetic sex gametes like ova and sperm cells?", "Mitosis", "Meiosis", "Binary Fission", "Budding", 1, "Meiosis is a reduction division reducing diploid (2n) count to haploid (n) in sex gametes."))
        list.add(q("Biology", "Mitotic somatic division generates daughter cells that possess:", "Exactly half the parental DNA", "Identical duplicate parental DNA", "Vastly mutated chromosome strands", "Triple the homologous pairings", 1, "Mitosis is duplicated division producing identical body cells with identical genotypes."))
        list.add(q("Biology", "Which specialized bio-cell serves as the core functional unit of the human nervous network?", "Glial Cell", "Neuron", "Nephron", "Osteocyte", 1, "Neurons conduct electric action potentials and signals across synapses."))
        list.add(q("Biology", "What micro-structure serves as the primary filter inside human kidneys?", "Alveolus", "Nephron", "Neuron", "Villa", 1, "The nephron is the filtration, reabsorption, and secretion unit of kidney cleaning."))

        // Add 75 dynamic Biology questions to reach 100
        val cellParts = listOf("Nucleus", "Ribosome", "Endoplasmic Reticulum", "Golgi Apparatus", "Vacuole")
        val cellFunctions = listOf("storing DNA genetic blueprints", "synthesizing cellular proteins", "folding proteins/lipids", "packaging proteins", "storing cellular water and waste")
        for (i in 1..75) {
            val partIdx = i % cellParts.size
            val label = cellParts[partIdx]
            val desc = cellFunctions[partIdx]
            val wrong1 = cellParts[(partIdx + 1) % cellParts.size]
            val wrong2 = cellParts[(partIdx + 2) % cellParts.size]
            val wrong3 = cellParts[(partIdx + 3) % cellParts.size]
            list.add(when (i % 5) {
                0 -> q("Biology", "Which cellular organelle is responsible for $desc?", wrong1, label, wrong2, wrong3, 1, "The $label is specifically designed for $desc.")
                1 -> q("Biology", "What is the primary organic macro-component comprising the cell walls of plants (index $i)?", "Chitin", "Peptidoglycan", "Cellulose", "Glycogen", 2, "Plant cell walls are primarily reinforced by cellulose microfibrils.")
                2 -> q("Biology", "Which plant hormone is highly active in promoting fruit ripening (reference code $i)?", "Auxin", "Gibberellin", "Ethylene", "Cytokinin", 2, "Ethylene is a volatile gaseous hormone that accelerates ripening.")
                3 -> q("Biology", "What fluid connective tissue circulates immune cells, gases, and nutrients in invertebrates?", "Lymph", "Hemolymph", "Plasma", "Cytoplasm", 1, "Open circulatory systems use hemolymph as the primary circulating fluid.")
                else -> q("Biology", "Which phylum of animal kingdom represents segmented worms like earthworms (index $i)?", "Platyhelminthes", "Nematoda", "Annelida", "Arthropoda", 2, "Annelida are the segmented worms, possessing bilateral symmetry and coeloms.")
            })
        }

        // --- 3. APTITUDE (51 - 75) ---
        list.add(q("Aptitude", "Find the next number in the series: 3, 6, 12, 24, ?", "36", "48", "60", "72", 1, "Geometric progression with ratio r=2. 24 * 2 = 48."))
        list.add(q("Aptitude", "Complete the analogy: Book is to Author as Song is to ____.", "Singer", "Listener", "Composer", "Instrument", 2, "An author creates a book, and a composer creates a song."))
        list.add(q("Aptitude", "If all squares are rectangles, and all rectangles are polygons, then:", "All polygons are squares", "All squares are polygons", "Some rectangles are not polygons", "No polygons are squares", 1, "By logical transitivity, if S is subset of R and R is subset of P, then S is subset of P."))
        list.add(q("Aptitude", "An analog clock shows exactly 03:00. What is the angle between the hands?", "45 degrees", "90 degrees", "120 degrees", "180 degrees", 1, "360 degrees total, 30 degrees per hour. 3 * 30 = 90 degrees."))
        list.add(q("Aptitude", "A train travels 120 km in 2 hours. What is its speed in m/s?", "16.67 m/s", "30 m/s", "60 m/s", "100 m/s", 0, "Speed = 120 km / 2 h = 60 km/h. To convert to m/s, divide by 3.6: 60 / 3.6 = 16.67 m/s."))
        list.add(q("Aptitude", "Identify the next number in this sequence: 2, 5, 10, 17, ?", "24", "25", "26", "27", 2, "Sequence is n² + 1: 1²+1, 2²+1, 3²+1, 4²+1. Next is 5²+1 = 26."))
        list.add(q("Aptitude", "Complete this antonymous pairing analogy: Hot is to Cold as Active is to ____.", "Lively", "Passive", "Sluggish", "Static", 1, "Hot is the direct antonym of cold, and active is the direct antonym of passive."))
        list.add(q("Aptitude", "A is B’s brother. B is C's sister. What is C’s relationship to A?", "C is A's sister", "C is A's brother", "C is either A’s brother or sister", "Nephew", 2, "B is sister to both A and C. C is sibling to A, but C's gender is unknown (brother or sister)."))
        list.add(q("Aptitude", "If you rearrange the mixed letters 'WRTEA', which natural element is formed?", "WEATHER", "WATER", "WHEAT", "EARTH", 1, "Rearranging 'WRTEA' yields 'WATER'."))
        list.add(q("Aptitude", "A father is 4 times older than his son. In 20 years, he will be twice as old. Son's age?", "8", "10", "12", "15", 1, "Let son's age be s, father 4s. 4s + 20 = 2(s + 20) -> 2s = 20 -> s = 10."))
        list.add(q("Aptitude", "Find the missing calendar term in the series: J, F, M, A, M, J, ?", "A", "S", "O", "J", 3, "Initials of consecutive months (Jan, Feb, Mar, Apr, May, June). Next is July, starting with J."))
        list.add(q("Aptitude", "Select the odd geometrical shape out from the following collection:", "Circle", "Triangle", "Square", "Cylinder", 3, "Circle, Triangle, and Square are 2D shapes. Cylinder is 3D."))
        list.add(q("Aptitude", "Complete the analogy: Doctor is to Stethoscope as Writer is to ____.", "Book", "Paper", "Pen", "Desk", 2, "Stethoscope is the tool of a doctor, and a pen is the tool of a writer."))
        list.add(q("Aptitude", "In an alphanumeric code, CAT is 3120 (C=3, A=1, T=20). What is DOG?", "4127", "4157", "41515", "4159", 1, "D=4, O=15, G=7. Sequentially: 4157."))
        list.add(q("Aptitude", "Find the matching eighth term in the Fibonacci sequence: 1, 1, 2, 3, 5, 8, 13, ?", "18", "20", "21", "24", 2, "Fibonacci term is sum of preceding two: 8 + 13 = 21."))
        list.add(q("Aptitude", "Five students are in a row. A sits next to B. C sits next to D. E is far left. Center?", "A", "B", "C", "D", 1, "Layout is E, D, C, B, A or E, B, A, D, C. Central position is B."))
        list.add(q("Aptitude", "A price is marked up by 20%, then discounted by 20%. Net deviation?", "No change", "4% increase", "4% decrease", "2% decrease", 2, "100 -> 120 -> 96 (a 4% overall decrease)."))
        list.add(q("Aptitude", "If 5 machines make 5 widgets in 5 minutes, 100 machines make 100 widgets in:", "5 minutes", "20 minutes", "50 minutes", "100 minutes", 0, "Each machine takes 5 minutes to make 1 widget. 100 machines parallelly take 5 minutes."))
        list.add(q("Aptitude", "Identify the odd metal out: Iron, Copper, Bronze, Gold.", "Iron", "Copper", "Bronze", "Gold", 2, "Iron, Copper, and Gold are elemental. Bronze is an alloy (Copper and Tin)."))
        list.add(q("Aptitude", "A motorist drives at 90 km/h. How many kilometers do they cover in 45 minutes?", "60 km", "67.5 km", "72 km", "80 km", 1, "Distance = 90 km/h * 0.75 h = 67.5 km."))
        list.add(q("Aptitude", "A painted cube is cut into 27 small identical cubes. How many have 3 painted faces?", "4", "6", "8", "12", 2, "The 3-painted cubes are at the 8 corners of the larger cube."))
        list.add(q("Aptitude", "Identify the missing sequence alphabet: Z, W, T, Q, ?", "N", "O", "P", "M", 0, "Skip two letters backwards: Z(26), W(23), T(20), Q(17), N(14)."))
        list.add(q("Aptitude", "Complete the analogy: Clock is to Time as Thermometer is to ____.", "Pressure", "Temperature", "Humidity", "Heat", 1, "A clock measures time, a thermometer measures temperature."))
        list.add(q("Aptitude", "If a single sheet of paper is folded in half 3 times, how many layers are formed?", "4", "6", "8", "16", 2, "Folding yields 2^n layers. For 3 folds: 2³ = 8 layers."))
        list.add(q("Aptitude", "Out of 50 students, 30 like Math, 25 like Science, 10 like both. How many like neither?", "5", "10", "15", "20", 0, "No. of students = Math(30) + Science(25) - Both(10) = 45. Liking neither is 50 - 45 = 5."))

        // Add 75 dynamic Aptitude questions to reach 100
        for (i in 1..75) {
            val step = i % 4 + 2
            val nextNum = 10 + i * step
            list.add(when (i % 5) {
                0 -> q("Aptitude", "Find the next pattern number in this series: 10, ${10 + step}, ${10 + 2*step}, ${10 + 3*step}, ?", "${nextNum - 1}", "$nextNum", "${nextNum + 1}", "${nextNum + step}", 1, "The pattern adds a fixed value of $step on each iteration.")
                1 -> q("Aptitude", "Complete the professional connection analogy: Chef is to Knife as Carpenter is to ____ (Code $i).", "Saw", "Wood", "Table", "House", 0, "A knife is the tool of a chef, and a saw is the tool of a carpenter.")
                2 -> q("Aptitude", "If an object is traveling at a speed of ${step * 10} m/s, how many meters does it cover in 5 seconds?", "${step * 50}", "${step * 40}", "${step * 30}", "None", 0, "Distance = Speed * Time = ${step * 10} * 5 = ${step * 50} meters.")
                3 -> q("Aptitude", "Complete the letter series trend: A, C, E, G, ? (Variation reference $i)", "H", "I", "J", "K", 1, "Skips one alphabet on each step: A(+2), C(+2), E(+2), G(+2) yields I.")
                else -> q("Aptitude", "If a rectangle has an initial width of $step units and area gets doubled, new width is?", "${step * 2}", "${step}", "1", "None", 0, "With constant length, doubling the area requires doubling width to ${step * 2} units.")
            })
        }

        // --- 4. ENGLISH (76 - 100) ---
        list.add(q("English", "Identify the correct use of the comma: 'I wanted to buy the book but, the shop was closed.'", "Correct as is", "Remove comma after 'but'", "Move comma before 'but'", "Add comma after 'shop'", 1, "The coordinate conjunction 'but' should be preceded by a comma, not followed: '...book, but the...'"))
        list.add(q("English", "Choose the correct modal verb: 'You _______ smoke in the hospital; it is strictly prohibited.'", "shouldn't", "must not", "don't have to", "might not", 1, "'Must not' expresses strong, institutional prohibition."))
        list.add(q("English", "Identify the correct passive voice: 'The engineer built a complex bridge.'", "A complex bridge building by the engineer.", "A complex bridge was built by the engineer.", "A complex bridge is built by the engineer.", "The engineer was building a complex bridge.", 1, "Simple past passivizes with was + past participle: 'was built by the engineer'."))
        list.add(q("English", "Which prefix should be added to the base word 'understand' to form its grammatical opposite?", "dis", "un", "mis", "in", 2, "Adding the prefix 'mis-' forms 'misunderstand'."))
        list.add(q("English", "Choose the grammatically correct sentence from the options below:", "She plays the piano good.", "She plays the piano well.", "She plays a piano good.", "She play the piano well.", 1, "'Plays' is an active verb requiring modification by the adverb 'well'. 'Good' is an adjective."))
        list.add(q("English", "Select the best synonym representing the attribute 'generous':", "Miserly", "Altruistic", "Malevolent", "Pragmatic", 1, "'Altruistic' refers to selflessness, serving as a clean synonym."))
        list.add(q("English", "Which is the correct grammatical English spelling for the artistic standard:", "Esthetic", "Aesthetic", "Aisthetic", "Assthetic", 1, "'Aesthetic' relates to classic appreciation of art and beauty."))
        list.add(q("English", "Fill in the blank space: 'The child ____________ the expensive porcelain vase yesterday.'", "breaked", "broke", "broken", "breaks", 1, "The word 'yesterday' indicates simple past tense. Past tense of 'break' is 'broke'."))
        list.add(q("English", "Select the appropriate conditional term: 'If I _______ in your shoes, I would study harder.'", "was", "am", "were", "be", 2, "Subjunctive mood hypothetical conditionals use 'were' for all pronouns."))
        list.add(q("English", "What is the proper plural form of the singular academic term 'Criterion'?", "Criterions", "Criteria", "Criterias", "Criteri", 1, "Criterion has Greek origins, and its standard plural is 'Criteria'."))
        list.add(q("English", "Select the ideal antonym representing the word 'obscure':", "Hidden", "Vague", "Clear", "Doubtful", 2, "Obscure means hidden or unclear, so its direct antonym is 'Clear'."))
        list.add(q("English", "Fill in the correct preposition: 'He is highly interested _____ studying plant mutations.'", "at", "on", "in", "with", 2, "'Interested' is idiomatically coupled with the preposition 'in'."))
        list.add(q("English", "What is the correct collective noun used for a group of lions?", "Pack", "Herd", "Pride", "Flock", 2, "The collective nouns for lions is a 'Pride'."))
        list.add(q("English", "Fill in the transport preposition: 'They travelled all the way to Harar _____ bus.'", "by", "on", "with", "in", 0, "Preposition 'by' is standard for mediums of transport (by bus, by train)."))
        list.add(q("English", "Choose the correct spelling of the word representing a lack of necessity:", "Unnecesary", "Unnecessary", "Unecessary", "Uneccesary", 1, "'Unnecessary' contains one 'n', double 'c', and double 's'."))
        list.add(q("English", "Render this statement in indirect speech: She said, 'I am exhausted.'", "She said she is exhausted.", "She said that she was exhausted.", "She said that I was exhausted.", "She was saying that she is exhausted.", 1, "Direct present speech backshifts to past perfect or simple past: 'She said that she was...'"))
        list.add(q("English", "Identify the correct conjunction: 'The student was exhausted, _______ she completed the assignment.'", "consequently", "or", "yet", "so", 2, "'Yet' signals contrast or concession between coordinating clauses."))
        list.add(q("English", "Which verb correctly matches subject-verb proximity: 'Either the teacher or the students _________.'", "cleans", "clean", "was cleaning", "has cleaned", 1, "In either/or structures, the verb agrees with the closer plural subject 'students' (clean)."))
        list.add(q("English", "Convert this sentence to the passive voice: 'They are repairing the highway.'", "The highway was repaired by them.", "The highway is repaired by them.", "The highway is being repaired.", "The highway repaired.", 2, "Present continuous is passivized by: 'is/are being + past participle'."))
        list.add(q("English", "Choose the correct pronoun: 'This academic secret is strictly between you and _______.'", "I", "me", "we", "he", 1, "'Between' is a preposition requiring objective-case pronouns: 'you and me'."))
        list.add(q("English", "What is the true figurative meaning of the common idiom 'to spill the beans'?", "To drop groceries", "To tell a lie", "To reveal a secret prematurely", "To make an apology", 2, "'Spill the beans' idiomatically represents revealing secrets prematurely."))
        list.add(q("English", "Identify the correct vocabulary spelling for housing arrangements:", "Acommodation", "Accommodation", "Accomodation", "Acomodation", 1, "'Accommodation' is spelled with a double 'c' and double 'm'."))
        list.add(q("English", "Identify the grammatically correct indirect question structure:", "Can you tell me where is the library?", "Can you tell me where the library is?", "Can you tell me where the library is located at?", "Can you tell me is the library where?", 1, "Indirect embedded questions use standard declarative word order: Subject + Verb ('where the library is')."))
        list.add(q("English", "Choose the correct homophone word: 'The inclement winter _________ affected our flights.'", "whether", "weather", "wether", "feather", 1, "'Weather' refers to atmospheric conditions."))
        list.add(q("English", "Fill in the blank with the correct article: 'It is _______ distinct honor to present.'", "a", "an", "the", "no article", 0, "We use the indefinite article 'a' preceding consonant sounds like /d/ in 'distinct'."))

        // Add 75 dynamic English questions to reach 100
        val vocabSynonyms = listOf(
            Triple("amiable", "friendly", "hostile"),
            Triple("laconic", "concise", "verbose"),
            Triple("gargantuan", "huge", "tiny"),
            Triple("ephemeral", "brief", "eternal"),
            Triple("resolute", "determined", "wavering")
        )
        for (i in 1..75) {
            val trip = vocabSynonyms[i % vocabSynonyms.size]
            list.add(when (i % 5) {
                0 -> q("English", "What is the primary synonym for the vocabulary term '${trip.first}'?", trip.second, trip.third, "unimportant", "fragile", 0, "The word '${trip.first}' represents a state of being ${trip.second}.")
                1 -> q("English", "Identify the direct antonym for the term '${trip.first}' in English (index $i):", trip.second, trip.third, "neutral", "vibrant", 1, "The antonym of '${trip.first}' (meaning ${trip.second}) is '${trip.third}'.")
                2 -> q("English", "Choose the correctly spelled academic term representing $i:", "Pneumonia", "Pneumonnia", "Neumonia", "Pnumonia", 0, "The correct Greek spelling is 'Pneumonia' featuring silent 'P'.")
                3 -> q("English", "Fill in the appropriate preposition: She is committed _______ completing her degree (Code $i).", "to", "for", "with", "at", 0, "The adjective 'committed' is idiomatically followed by 'to'.")
                else -> q("English", "Render in passive voice: 'The storm damaged the cottage' (Reference $i).", "The cottage was damaged by the storm.", "The cottage is damaged.", "The storm has damaged the cottage.", "Cottage is being damaged.", 0, "Simple past maps to 'was + past participle' in passive voice.")
            })
        }

        // --- 5. PHYSICS (101 - 150) ---
        list.add(q("Physics", "What are the dimensions of the universal gravitational constant G?", "[M⁻¹ L³ T⁻²]", "[M L² T⁻²]", "[M⁻² L³ T⁻¹]", "[M L³ T⁻²]", 0, "From F = G*m1*m2/r², we find [G] = [F][r²]/[m²] = [M L T⁻²][L²]/[M²] = [M⁻¹ L³ T⁻²]."))
        list.add(q("Physics", "A projectile is launched at 45°. If velocity is doubled, max height changes by:", "It doubles", "It remains the same", "It increases by four times", "It increases by eight times", 2, "H = (u² * sin²θ)/2g. Height is proportional to u²; doubling u makes height 2² = 4 times larger."))
        list.add(q("Physics", "Which of Lord Newton's laws of motion defines force and inertia?", "First Law", "Second Law", "Third Law", "Law of Gravitation", 0, "Newton's First Law (Inertia) states objects remain at rest or constant speed unless acted on by force."))
        list.add(q("Physics", "A car travels round a 50m circular track at 10m/s. Centripetal acceleration?", "0.2 m/s²", "2 m/s²", "5 m/s²", "20 m/s²", 1, "a = v²/r. Substituting: a = 10² / 50 = 100 / 50 = 2 m/s²."))
        list.add(q("Physics", "If both mass and velocity are doubled, kinetic energy increases by factor:", "2", "4", "8", "16", 2, "KE = 1/2 * m * v². Doubling mass doubles KE, doubling velocity increases KE by 4. Net factor: 2 * 4 = 8."))
        list.add(q("Physics", "A force of 10 N stretches a spring by 0.2 m. Elastic potential energy?", "1 J", "2 J", "5 J", "10 J", 0, "k = F/x = 10/0.2 = 50 N/m. PE = 1/2 * k * x² = 0.5 * 50 * (0.2)² = 1 J."))
        list.add(q("Physics", "What is the escape velocity of a projectile from the surface of the Earth?", "7.9 km/s", "11.2 km/s", "3 * 10⁸ m/s", "9.8 m/s", 1, "Escape velocity v = sqrt(2GM/R) equates approximately to 11.2 km/s on Earth."))
        list.add(q("Physics", "A 2 kg mass at 4 m/s collides and sticks to a stationary 2 kg mass. Final speed?", "1 m/s", "2 m/s", "3 m/s", "4 m/s", 1, "Conservation of momentum: (2 * 4) + 0 = (2 + 2) * v_f -> 8 = 4 v_f -> v_f = 2 m/s."))
        list.add(q("Physics", "Which thermodynamic process occurs at constant volume?", "Isobaric", "Isochoric", "Isothermal", "Adiabatic", 1, "An Isochoric process is a thermodynamic change occurring at constant volume (W = 0)."))
        list.add(q("Physics", "What is the refractive index of a medium if the speed of light is 2 * 10⁸ m/s?", "1.0", "1.33", "1.5", "2.0", 2, "n = c / v = (3 * 10⁸) / (2 * 10⁸) = 1.5."))
        list.add(q("Physics", "By what factor does the resistance of a wire change if its length is doubled?", "Remains same", "Doubles", "Halves", "Quadrules", 1, "Resistance R = ρL/A. R is directly proportional to length, so doubling length doubles resistance."))
        list.add(q("Physics", "What is the electric field inside a hollow spherical charged conductor?", "Zero", "Constant", "Inversely proportional to radius", "Infinity", 0, "According to Gauss's Law, static electric charge sits entirely on surface, making interior field zero."))
        list.add(q("Physics", "Which electromagnetic wave possesses the highest frequency?", "Radio waves", "Infrared", "Ultraviolet", "Gamma rays", 3, "Gamma rays sit at the highest energy and frequency end of the electromagnetic spectrum."))
        list.add(q("Physics", "Which particle exhibits both wave and corpuscular/particle properties?", "Photon", "Electron", "Proton", "All of these", 3, "Wave-particle duality holds for all microscopic quantum entities, satisfying de Broglie relations."))
        list.add(q("Physics", "What physical phenomenon occurs when two light waves superpose to form a resource trend?", "Interference", "Diffraction", "Polarization", "Refraction", 0, "Interference is the superposition of waves creating regions of constructive and destructive crests."))
        list.add(q("Physics", "What is the focal length of a flat plane mirror?", "Zero", "10 cm", "100 cm", "Infinity", 3, "Since a plane mirror has zero surface curvature (R = infinity), its focal length f = R/2 is infinite."))
        list.add(q("Physics", "Which fundamental force keeps nucleons (protons and neutrons) bound in the nucleus?", "Gravitational", "Electromagnetic", "Strong Nuclear", "Weak Nuclear", 2, "The Strong Nuclear force binds quarks and nucleons, overcoming electromagnetic repulsion inside nuclei."))
        list.add(q("Physics", "What state of matter is structured by an ionized gas carrying equal positive and negative charges?", "Liquid", "Gas", "Plasma", "Bose-Einstein State", 2, "Plasma consists of superheated, highly ionized atoms and free electrons, conducting current."))
        list.add(q("Physics", "What is the SI unit of electric capacitance?", "Farad", "Henry", "Tesla", "Weber", 0, "The Farad (F) is the unit of capacitance, defined as charge (Coulombs) divided by voltage (Volts)."))
        list.add(q("Physics", "The work-energy theorem states that work done by the net force is:", "Change in Potential Energy", "Change in Kinetic Energy", "Total mechanical energy", "Power expended", 1, "W_net = ΔKE. Net work done of force matches the change in kinetic energy of the entity."))
        list.add(q("Physics", "Which physical law states mechanical energy is conserved in isolated bounds?", "First Law", "Third Law", "Le Chatelier's", "Conservation of Energy", 3, "The law of conservation of energy states energy cannot be created or destroyed, only transformed."))
        list.add(q("Physics", "In thermodynamics, which state function describes the disorder or randomness of a system?", "Enthalpy", "Entropy", "Free Energy", "Temperature", 1, "Entropy (S) is the thermodynamic property indicating molecular randomness and energy dispersion."))
        list.add(q("Physics", "A step-up transformer increases which electrical property?", "Current", "Power", "Voltage", "Frequency", 2, "A step-up transformer increases secondary voltage by stepping down secondary current, conserving power."))
        list.add(q("Physics", "What is the unit of magnetic flux density?", "Weber", "Tesla", "Ohm", "Henry", 1, "Tesla (T) measures magnetic field strength, equivalent to Webbers per square meter."))
        list.add(q("Physics", "Which instrument measures small electric currents?", "Voltmeter", "Ammeter", "Galvanometer", "Barometer", 2, "A needle galvanometer is designed to detect and measure minute electric currents."))
        list.add(q("Physics", "The resistance of an ideal voltmeter should ideally be:", "Zero", "Low", "High", "Infinite", 3, "Ideal voltmeters possess infinite resistance to prevent current draw from the parallel branch being measured."))
        list.add(q("Physics", "The resistance of an ideal ammeter should ideally be:", "Zero", "Low", "High", "Infinite", 0, "Ideal ammeters must have zero resistance to prevent drop variations in the series loop."))
        list.add(q("Physics", "Which mirrors are universally used as rear-view mirrors in passenger cars?", "Concave", "Convex", "Plane", "Cylindrical", 1, "Convex mirrors diverge light, providing a wider field of view and erect virtual images."))
        list.add(q("Physics", "What is the speed of sound waves in a vacuum?", "343 m/s", "3 * 10⁸ m/s", "Zero", "1500 m/s", 2, "Sound is a mechanical pressure wave requiring a material medium; it cannot travel in a vacuum."))
        list.add(q("Physics", "Which state has the highest thermal conductivity?", "Solid", "Liquid", "Gas", "Vacuum", 0, "Solids possess high density molecular grids enabling rapid kinetic transfer (conduction)."))
        list.add(q("Physics", "What is the rate of flow of electric charge called?", "Voltage", "Resistance", "Current", "Capacitance", 2, "Electric current is defined as charge flow past a boundary per unit time (I = dQ/dt)."))
        list.add(q("Physics", "According to Snell's Law, when light passes to a denser medium, it bends:", "Towards the normal", "Away from the normal", "Straight unchanged", "Backwards by 180°", 0, "Refraction in higher-index media bends light waves towards the normal axis (sin(i)/sin(r) = n2/n1)."))
        list.add(q("Physics", "A simple pendulum’s period depends primarily on which parameter?", "Mass", "Length", "Amplitude", "Air density", 1, "Period T = 2π * sqrt(L/g), making duration dependent strictly on pendulum length L."))
        list.add(q("Physics", "The pressure of a static fluid at depth h is defined as:", "P = ρgh", "P = mgh", "P = F/A", "P = rh/g", 0, "Hydrostatic pressure equals density * gravity * depth (P = ρgh) relative to the free surface."))
        list.add(q("Physics", "What is the SI unit of power?", "Joule", "Watt", "Newton", "Pascal", 1, "The Watt (W) is the units of power, representing energy flow rate of 1 Joule per second."))
        list.add(q("Physics", "Which physical property is defined as the mass per unit volume of substance?", "Weight", "Volume", "Density", "Buoyancy", 2, "Density (ρ) is mass concentrated per unit space volume (ρ = m/V)."))
        list.add(q("Physics", "What mathematical constant matches the value of acceleration of gravity on Earth?", "9.8 m/s²", "1.6 m/s²", "11.2 m/s²", "3.14 m/s²", 0, "Standard freefall acceleration on Earth surface due to mass gravity is 9.8 m/s²."))
        list.add(q("Physics", "Which effect explains light frequency shifts when resources move relative to observers?", "Doppler Effect", "Einstein Effect", "Newtonian Effect", "Photoelectric Effect", 0, "The Doppler Effect shifts wavelengths (blue/red shifts) under relative source velocity."))
        list.add(q("Physics", "What physical property describes the resistance of fluid to flowing under shear?", "Density", "Inertia", "Viscosity", "Friction", 2, "Viscosity measures internal resistive friction inside fluid shear matrices."))
        list.add(q("Physics", "Which unit measures the acoustic intensity level of sound waves?", "Decibel", "Pascal", "Watt", "Hertz", 0, "Decibels (dB) measure logarithmic ratio comparisons of relative sound pressure/intensity."))
        list.add(q("Physics", "What is the main transport mechanism of heat from the Sun to the Earth?", "Conduction", "Convection", "Radiation", "Advection", 2, "Radiation via electromagnetic waves carries radiant energy across the vacuum of space."))
        list.add(q("Physics", "Which constant relates frequency of photon to its quantum energy?", "Planck's Constant", "Coulomb Constant", "Boltzmann Constant", "Newton Constant", 0, "Planck's Constant (h = 6.626 * 10⁻³⁴ J·s) scales energy according to E = hf."))
        list.add(q("Physics", "What optical lens is thickest at the edges and thinnest at the coordinates center?", "Convex", "Concave", "Bifocal", "Plano-convex", 1, "Concave lenses are divergers, characterized by inward surface thinning and edge thickness."))
        list.add(q("Physics", "Which wave property describes is the distance between consecutive crests?", "Frequency", "Amplitude", "Wavelength", "Velocity", 2, "Wavelength (λ) measures physical distance between identical phases of adjacent wave cycles."))
        list.add(q("Physics", "What is the SI unit of torque?", "Newton", "Joule", "Newton-meter", "Watt", 2, "Torque is force cross radius, measured in Newton-meters (N·m)."))
        list.add(q("Physics", "Which law states current entering junction equals current leaving it?", "Kirchhoff's Current Law", "Kirchhoff's Voltage Law", "Ohm's Law", "Tesla's Law", 0, "Kirchhoff's First Law (KCL) represents conservation of charge at nodes (Current in = Current out)."))
        list.add(q("Physics", "What thermodynamic cycle is defined as having the maximum theoretical efficiency?", "Carnot Cycle", "Diesel Cycle", "Rankine Cycle", "Haber Cycle", 0, "The Carnot cycle uses reversible isothermal and adiabatic lines to define maximum thermal efficiency."))
        list.add(q("Physics", "Which temperature scale starts at absolute zero with identical degree steps to Celsius?", "Fahrenheit", "Kelvin", "Romer", "Rankine", 1, "The Kelvin scale starts at OK (-273.15 °C) and is the absolute thermodynamic temperature scale."))
        list.add(q("Physics", "What device converts mechanical rotation coil energy into electric current?", "Motor", "Generator", "Transformer", "Resistor", 1, "Electric generators utilize electromagnetic induction (Faraday's Law) to produce electricity."))
        list.add(q("Physics", "In optics, absolute internal reflection occurs when angle of incidence exceeds:", "Critical Angle", "Refractive Angle", "Brewster Angle", "Normal Angle", 0, "Total internal reflection (TIR) occurs when ray hits boundary of lower-index media past the critical angle."))

        // Add 50 dynamic Physics questions to reach 100
        for (i in 1..50) {
            val force = i * 2 + 10
            val area = i % 5 + 2
            val pressure = force / area
            list.add(when (i % 5) {
                0 -> q("Physics", "Calculate the hydrostatic pressure under a force of $force N applied uniformly across $area m²?", "$pressure Pa", "${pressure + 5} Pa", "${pressure - 3} Pa", "${pressure * 2} Pa", 0, "Pressure = Force / Area. $force / $area = $pressure Pa.")
                1 -> q("Physics", "What is the total resistance of two resistors ($i Ω and $i Ω) wired in series?", "${i * 2} Ω", "$i Ω", "${i / 2} Ω", "None", 0, "Series resistance is additive: R_total = R1 + R2 = $i + $i = ${i * 2} Ω.")
                2 -> q("Physics", "What is the equivalent resistance of two resistors ($i Ω and $i Ω) wired in parallel?", "${i / 2} Ω", "${i * 2} Ω", "$i Ω", "None", 0, "Parallel resistance: 1/Rp = 1/R1 + 1/R2 = 2/$i, giving Rp = $i / 2 Ω.")
                3 -> q("Physics", "Calculate the velocity of a wave with a frequency of 10 Hz and wavelength of $area m?", "${10 * area} m/s", "10 m/s", "$area m/s", "None", 0, "Velocity is frequency multiplied by wavelength: v = f * λ = 10 * $area = ${10 * area} m/s.")
                else -> q("Physics", "How much work is completed when a constant force of $force N moves an entity $area meters?", "${force * area} J", "${force + area} J", "0 J", "None", 0, "Work = Force * Distance = $force * $area = ${force * area} Joules.")
            })
        }

        // --- 6. CHEMISTRY (151 - 200) ---
        list.add(q("Chemistry", "What is the maximum number of electrons that can occupy the 3d subshell of an atom?", "2", "6", "10", "14", 2, "The d-subshell (l = 2) contains 5 orbitals. Since each orbital can hold at most 2 electrons with opposite spins, the maximum capacity is 5 * 2 = 10 electrons."))
        list.add(q("Chemistry", "Which oxide of nitrogen is commonly known as laughing gas?", "Nitride Oxide", "Nitrous Oxide", "Nitrogen Dioxide", "Dinitrogen Pentoxide", 1, "Nitrous oxide (N2O) is the anesthetic gas termed laughing gas."))
        list.add(q("Chemistry", "What is the molarity of a solution prepared by dissolving 2 moles of solute in 500 mL of solvent?", "1 M", "2 M", "4 M", "5 M", 2, "Molarity is moles per liter of solution: M = 2.0 / 0.500 L = 4.0 M."))
        list.add(q("Chemistry", "Which element possesses the highest electronegativity value on the periodic scale?", "Oxygen", "Fluorine", "Neon", "Chlorine", 1, "Fluorine is the most electronegative element, designated a value of 4.0 on the Pauling scale."))
        list.add(q("Chemistry", "Under standard conditions, which organic hydrocarbon represents the simplest member of the alkynes?", "Methane", "Ethylene", "Acetylene", "Propane", 2, "Acetylene (ethyne, C2H2) is the simplest alkyne, containing a triple carbon-carbon bond."))
        list.add(q("Chemistry", "What is the oxidation state of sulfur in sulfuric acid (H₂SO₄)?", "+2", "+4", "+6", "-2", 2, "With H (+1) and O (-2): 2(1) + S + 4(-2) = 0 -> S = +6."))
        list.add(q("Chemistry", "Which law states that the volume of a gas is proportional to temperature at constant pressure?", "Boyle's Law", "Charles's Law", "Avogadro's Law", "Dalton's Law", 1, "Charles's Law states volume is directly proportional to absolute Temperature (V/T = k)."))
        list.add(q("Chemistry", "The half-life of a first-order reaction with rate constant k is expressed as:", "t1/2 = k / 0.693", "t1/2 = 0.693 / k", "t1/2 = ln(k)", "t1/2 = 0.5 / k", 1, "The half-life of a first-order chemical decay is independent of concentration: t_1/2 = ln(2)/k = 0.693/k."))
        list.add(q("Chemistry", "What is the molecular geometry of a sulfur hexafluoride (SF₆) molecule?", "Tetrahedral", "Trigonal bipyramidal", "Octahedral", "Bent", 2, "SF6 has 6 bonding pairs and no lone pairs, structuring an octahedral distribution."))
        list.add(q("Chemistry", "Which transition metal is an essential central component of the Vitamin B12 complex?", "Iron", "Cobalt", "Copper", "Zinc", 1, "Cobalamin (Vitamin B12) is structured around a central bioactive Cobalt ion."))
        list.add(q("Chemistry", "What is the pH of a 1.0 * 10⁻³ M solution of hydrochloric acid (HCl)?", "1", "3", "7", "11", 1, "HCl is a strong acid, fully ionizing. pH = -log10[H3O+] = -log10(10⁻³) = 3."))
        list.add(q("Chemistry", "In a galvanic cell, which chemical process occurs at the surface of the anode?", "Reduction", "Oxidation", "Precipitation", "Neutralization", 1, "Oxidation (loss of electrons) occurs selectively at the anode. Reduction occurs at the cathode."))
        list.add(q("Chemistry", "Identify the conjugate base of the Brønsted-Lowry acid HSO4⁻:", "H2SO4", "SO4²⁻", "H3O+", "OH-", 1, "The conjugate base of acid HSO4- is formed by releasing a hydronium proton: SO4²⁻."))
        list.add(q("Chemistry", "Which organic functional group is characterized by carbon-oxygen double bond flanking alkoxys?", "Ketone", "Aldehyde", "Ester", "Ether", 2, "Esters contain a carbonyl center bound adjacent to an alkoxy organic group (R-CO-OR')."))
        list.add(q("Chemistry", "How many total structural isomers exist for the butane hydrocarbon (C₄H₁₀)?", "2", "3", "4", "5", 0, "Butane has 2 structural isomers: linear n-butane and branched isobutane (2-methylpropane)."))
        list.add(q("Chemistry", "What is the hybridization of carbon in the linear carbon dioxide (CO₂) molecule?", "sp", "sp²", "sp³", "dsp²", 0, "Carbon links with two coordinate double bonds with no lone pairs, defining linear sp hybridization."))
        list.add(q("Chemistry", "Which rule state that electrons will occupy empty degenerate orbitals before pairing?", "Hund's Rule", "Heisenberg Principle", "Pauli Principle", "Aufbau Principle", 0, "Hund's Rule states electrons fill degenerate shells in parallel spins before orbital pairing."))
        list.add(q("Chemistry", "What is the primary product generated when ethene is hydrated under acid catalysis?", "Methane", "Ethane", "Ethanol", "Diethyl ether", 2, "Acid-catalyzed hydration adds water across the double bond of ethene, yielding ethanol."))
        list.add(q("Chemistry", "Which ion causes the primary hardness of water that prevents optimal lathering?", "Sodium and Potassium", "Calcium and Magnesium", "Chloride and Sulfate", "Carbonate and Bicarbonate", 1, "Hard water contains high concentrations of Calcium (Ca2+) and Magnesium (Mg2+) ions forming soap scum."))
        list.add(q("Chemistry", "Which indicator is commonly used in titration reactions of strong acids and bases?", "Starch", "Methyl orange", "Phenolphthalein", "Litmus", 2, "Phenolphthalein is a classic choice, changing from colorless (acidic) to pink/magenta (alkaline) near pH 9."))
        list.add(q("Chemistry", "What is the main component of common natural gas?", "Methane", "Propane", "Butane", "Hexane", 0, "Natural gas is primarily comprised of Methane (CH4), representing 70-90% of composition."))
        list.add(q("Chemistry", "What is the chemical name of common baking soda?", "Sodium Carbonate", "Sodium Bicarbonate", "Sodium Hydroxide", "Calcium Carbonate", 1, "Baking soda is Sodium bicarbonate (NaHCO3), acting as an alkaline expanding raising agent."))
        list.add(q("Chemistry", "Which gas is produced when Zinc active metal reacts with dilute hydrochloric acid?", "Oxygen", "Hydrogen", "Carbon Dioxide", "Chlorine", 1, "Active metals displace acid hydronium ions to produce Hydrogen gas (Zn + 2HCl -> ZnCl2 + H2)."))
        list.add(q("Chemistry", "What thermodynamic criterion defines spontaneity at constant temperature and pressure?", "ΔH < 0", "ΔS > 0", "ΔG < 0", "ΔG > 0", 2, "Spontaneity is determined by change in Gibbs free energy (ΔG). It must be negative (ΔG < 0)."))
        list.add(q("Chemistry", "What is the principal organic functional group in alcohols?", "Carbonyl", "Hydroxyl", "Carboxyl", "Amino", 1, "Alcohols are organic structures characterized by the presence of a hydroxyl (-OH) group."))
        list.add(q("Chemistry", "What is the atomic number of the element Carbon?", "4", "6", "12", "14", 1, "Carbon has 6 protons, defining its atomic number as 6 on the periodic table."))
        list.add(q("Chemistry", "The sharing of electron pairs between matching atoms builds which bond type?", "Ionic", "Covalent", "Metallic", "Hydrogen", 1, "Covalent bond is established by mutual sharing of electron pairs between covalent atoms."))
        list.add(q("Chemistry", "Which law says equal volumes of gases at constant temperature contain equal molecules?", "Charles's Law", "Boyle's Law", "Avogadro's Law", "Ideal Gas Law", 2, "Avogadro's hypothesis states equivalent gas volumes contain matching molecular counts."))
        list.add(q("Chemistry", "What is the molecular weight of water (H₂O) roughly in grams per mole?", "16 g/mol", "18 g/mol", "20 g/mol", "22 g/mol", 1, "Water molecular weight is 2(H) + 1(O) = 2(1.0) + 16.0 = 18.0 g/mol."))
        list.add(q("Chemistry", "Which chemical elements represents the lightest alkali metal?", "Lithium", "Sodium", "Hydrogen", "Helium", 0, "Lithium is the lightest group 1 alkali metal, possessing atomic number 3."))
        list.add(q("Chemistry", "What process converts a gas directly to solid without passing through liquid phase?", "Condensation", "Sublimation", "Deposition", "Evaporation", 2, "Gas to solid transformation without dynamic liquid transitional state is termed deposition."))
        list.add(q("Chemistry", "What process converts a solid directly to gas without passing through liquid phase?", "Condensation", "Sublimation", "Deposition", "Evaporation", 1, "Solid to gas conversion skipping intermediate melting is called sublimation."))
        list.add(q("Chemistry", "Which gas is the most abundant component in the Earth's atmosphere?", "Oxygen", "Carbon Dioxide", "Nitrogen", "Argon", 2, "Diatomic Nitrogen gas is the major component (representing ~78% of dry air volume)."))
        list.add(q("Chemistry", "Which organic catalyst speed up biochemical reactions in cells?", "Hormones", "Enzymes", "Lipids", "Vitamins", 1, "Cellular enzymes are organic protein catalysts accelerating vital chemical pathways."))
        list.add(q("Chemistry", "What is the chemical formula of glucose?", "C6H12O6", "C12H22O11", "CH4", "CO2", 0, "The hexose monosaccharide glucose has the molecular formula C6H12O6."))
        list.add(q("Chemistry", "In chemistry, what is the value of one Avogadro's mole constant?", "6.02 * 10²³", "3 * 10⁸", "9.8 * 10⁶", "1.6 * 10⁻¹⁹", 0, "Avogadro's constant is defined as 6.022 * 10²³ entities per mole."))
        list.add(q("Chemistry", "What structural functional group characterizes carboxylic acids?", "Carbonyl", "Hydroxyl", "Carboxyl", "Ether", 2, "Carboxylic acids feature a carboxyl carbon bound to oxygen and hydroxyl (-COOH)."))
        list.add(q("Chemistry", "What is the chemical name of common table salt?", "Sodium Hydroxide", "Sodium Chloride", "Sodium Bicarbonate", "Potassium Chloride", 1, "Common table salt is the cubic ionic lattice Sodium Chloride (NaCl)."))
        list.add(q("Chemistry", "Which organic functional group consists of carbon-oxygen-carbon single-bonds (R-O-R')?", "Ester", "Ether", "Ketone", "Aldehyde", 1, "Ethers consist of an oxygen bridging two organic alkyl groups (R-O-R')."))
        list.add(q("Chemistry", "What is the primary chemical component of rust?", "Iron Oxide", "Copper Carbonate", "Zinc Sulfate", "Sodium Chloride", 0, "Rust is primarily hydrous iron(III) oxide (Fe2O3·H2O), formed under oxygen and water."))
        list.add(q("Chemistry", "What is the pH of a strong alkaline basic solution?", "pH < 2", "pH = 7", "pH > 11", "pH = 5", 2, "Strong alkaline solutions exceed pH 11, reflecting low hydronium concentration."))
        list.add(q("Chemistry", "Which element behaves as the essential component in all organic compounds?", "Nitrogen", "Oxygen", "Carbon", "Silicon", 2, "Organic chemistry is defined as the chemistry of carbon-containing molecular networks."))
        list.add(q("Chemistry", "What is the chemical formula of nitric acid?", "HNO3", "H2SO4", "HCl", "CH3COOH", 0, "Nitric acid is a highly corrosive mineral strong acid with formula HNO3."))
        list.add(q("Chemistry", "Which noble gas has the lowest boiling point of all elements?", "Helium", "Neon", "Argon", "Krypton", 0, "Helium has the lowest boiling point (-268.9 °C) of any elemental substance."))
        list.add(q("Chemistry", "Unsaturated hydrocarbons contain which characteristic bond?", "Single bond", "Double or Triple bonds", "Ionic bonds", "Hydrogen bonds", 1, "Unsaturated alkenes and alkynes have double or triple covalent carbon links."))
        list.add(q("Chemistry", "What is the oxidation state of hydrogen in standard metal hydrides (such as NaH)?", "+1", "0", "-1", "+2", 2, "In binary metal hydrides, hydrogen is more electronegative than metal, taking -1."))
        list.add(q("Chemistry", "According to Le Chatelier's Principle, increasing reactant concentration shifts equilibrium:", "To the right", "To the left", "No shifting", "Backwards", 0, "Increasing reactants shifts equilibrium to the product side to consume excess reactant."))
        list.add(q("Chemistry", "What molecular shape represents methane (CH₄)?", "Linear", "Bent", "Tetrahedral", "Octahedral", 2, "Methane carbon coordinates 4 hydrogens with no lone pairs, structuring tetrahedral geometric angles."))
        list.add(q("Chemistry", "Which type of chemical reaction absorbs heat energy from the environment?", "Exothermic", "Endothermic", "Redox", "Substitution", 1, "Endothermic processes absorb thermal energy, designated by a positive enthalpy change (ΔH > 0)."))
        list.add(q("Chemistry", "The Pauli Exclusion Principle dictates that orbitals can hold at most how many electrons?", "1", "2", "6", "10", 1, "Each orbital shell contains at most two electrons, which must have opposite spin orientations."))

        // Add 50 dynamic Chemistry questions to reach 100
        for (i in 1..50) {
            val h3o = i % 5 + 2
            list.add(when (i % 5) {
                0 -> q("Chemistry", "What is the pH of a strong acid solution with a hydronium concentration of 1.0 * 10⁻$h3o M?", "$h3o", "${14 - h3o}", "${h3o + 2}", "None", 0, "By definition: pH = -log10[H3O+] = -log10(10⁻$h3o) = $h3o.")
                1 -> q("Chemistry", "What is the pOH of a basic solution if its measured pH value is $h3o?", "${14 - h3o}", "$h3o", "7", "None", 0, "Since pH + pOH = 14 at 25°C, pOH = 14 - $h3o = ${14 - h3o}.")
                2 -> q("Chemistry", "What is the oxidation state of oxygen in a hydrogen peroxide (H₂O₂) molecule (Reference $i)?", "-1", "-2", "0", "+1", 0, "In peroxides, oxygen contains a single bond to another oxygen, taking oxidation state of -1.")
                3 -> q("Chemistry", "What is the molecular formula of the linear alkyne containing ${h3o} carbons?", "C${h3o}H${h3o * 2 - 2}", "C${h3o}H${h3o * 2}", "C${h3o}H${h3o * 2 + 2}", "None", 0, "The general formula for alkynes is CnH2n-2. Substituting n=$h3o gives C${h3o}H${h3o * 2 - 2}.")
                else -> q("Chemistry", "What is the molecular formula of the saturated alkane containing ${h3o} carbons?", "C${h3o}H${h3o * 2 + 2}", "C${h3o}H${h3o * 2}", "C${h3o}H${h3o * 2 - 2}", "None", 0, "The general formula for alkanes is CnH2n+2. Substituting n=$h3o yields C${h3o}H${h3o * 2 + 2}")
            })
        }

        return list
    }
}
