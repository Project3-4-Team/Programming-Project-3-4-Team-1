# Programming Project 3: Knapsack Problem — Complete JUnit 5 Test Results

**Team Name:** Team 1  
**Team Members:** Moriancumer Hurtado, Jacob Gorham, Mubarak Alrashdi, Luis Agramonte Peralta  
**Course:** CS 2430-502, Fall 2026  
**Test Framework:** JUnit 5 (`KnapsackTestSuite.java`)  

---

## Executive Summary

| Test Category | Test Cases Covered | Status |
| :--- | :--- | :--- |
| **1. Core Model & Greedy Algorithms** | Encapsulation, `toString()` formatting, sorting comparators, and tie-breakers | **PASS** |
| **2. Week 3 Order-Change Revised Rules** | Solar instrument cap (`{2, 3, 7}`), thermal mass credit (`{6, 12}`), and deferred `11a` prerequisite | **PASS** |
| **3. Brute Force Exhaustive Search** | Full 2¹² = 4,096 baseline subsets and 2¹³ = 8,192 revised subsets (including infeasible candidates) | **PASS** |
| **4. Boundary & Edge Cases** | `800.0 kg` oversized single item and identical ratios (`10kg/1` vs `20kg/2`) | **PASS** |

---

## Section 1: Core Model & Greedy Strategy Tests

### Test Case 1: Experiment Data Encapsulation & Representation
* **Objective:** Verify data model integrity (all fields strictly `private` with validated getters/setters) and exact `String.format` output spacing and decimal precision.
* **JUnit Method:** `CoreTests.testExperimentEncapsulationAndToString()`
* **Input Data:**
  ```java
  new Experiment("1", "Cloud Patterns", 36.0, 5)
  ```
* **Expected Result:**
  * All declared fields (`order`, `experimentName`, `weightKg`, `rating`) use the `private` access modifier.
  * Calling `.toString()` yields the exact 30-character left-aligned name padding and 1-decimal weight format:
    ```text
    Experiment #1: Cloud Patterns                 (Weight: 36.0, Rating: 5)
    ```
* **Actual Result:** Reflection confirmed all 4 fields are `private`, and `.toString()` matched character-for-character.
* **Status:** **PASS**

### Test Case 2: Greedy Strategy Tie-Breaking Mechanics
* **Objective:** Ensure secondary comparators break ties deterministically without prioritizing suboptimal variants when primary metrics match.
* **JUnit Methods:**
  * `CoreTests.testHighestRatingFirstSortingAndLighterTieBreaker()`
  * `CoreTests.testLightestFirstSortingAndHigherRatingTieBreaker()`
  * `CoreTests.testBestRatioDoubleRatiosAndDescendingSort()`
* **Input Data & Expected Execution Sequence:**
  * **`HighestRatingFirst`:** Given two Rating `8` items (`100.0 kg` vs. `50.0 kg`), must prioritize the lighter item (`50.0 kg < 100.0 kg`).
  * **`LightestFirst`:** Given two `50.0 kg` items (Rating `3` vs. Rating `9`), must prioritize the higher-rated item (`9 > 3`).
  * **`BestRatingtoWeightRatioFirst`:** Computes floating-point ratios (`(double) rating / weightKg`), sorts descending (`0.9000` before `0.5000`), and breaks identical ratio ties by descending rating.
* **Actual Result:** All three comparators ordered tied elements according to specification and did not mutate the original input arrays.
* **Status:** **PASS**

---

## Section 2: Week 3 Order-Change Revised Rules Tests

### Test Case 3: Sample Manifest Validation & Prerequisite Buffering
* **Objective:** Verify enforcement of the three Week 3 payload constraints across `RevisedRules` and all greedy strategies.
* **JUnit Methods:**
  * `RevisedRuleTests.testSampleManifestSolarLimitReject()`
  * `RevisedRuleTests.testSampleManifest11aPrerequisiteReject()`
  * `RevisedRuleTests.testSampleManifestThermalMassCreditAccept()`
  * `RevisedRuleTests.testExperiment11aPrerequisiteBehavior()`

| Rule Tested | Candidate Manifest IDs | Expected Outcome | Actual Outcome | Status |
| :--- | :--- | :--- | :--- | :--- |
| **Solar Instrument Limit** (Max 2 of `{2, 3, 7}`) | `{2, 3, 7}` | Reject 3rd solar item (`7`), keeping total solar count at 2 (`452.0 kg`) | Accepted `{2, 3}` (`452.0 kg`, Rating `15`); rejected `{7}` | **PASS** |
| **Experiment `11a` Prerequisite** (`11a` requires `11`) | `{11a}` | Reject `11a` when `11` is absent (`0.0 kg`, Rating `0`) | Deferred `11a`; since `11` never arrived, final manifest empty (`0.0 kg`, Rating `0`) | **PASS** |
| **Deferred `11a` Re-evaluation** (`11a` arrives before `11`) | `{11a, 11}` | Buffer `11a`, then immediately add `11a` once `11` is accepted (`90.0 kg`) | `11a` buffered on arrival; once `11` (`80.0 kg`) accepted, `11a` (`10.0 kg`) auto-added (`90.0 kg`, Rating `12`) | **PASS** |
| **Thermal Mass Credit** (`-7.0 kg` when `{6, 12}` both chosen) | `{1, 2, 4, 6, 10, 12}` | Accept all 6; `-7.0 kg` credit reduces `707.0 kg` to `700.0 kg` | Accepted all 6; credit applied once; final mass **`700.0 kg / 700.0 kg`**, Rating **`39`** | **PASS** |

---

## Section 3: Brute Force Exhaustive Search Tests

### Test Case 4: Exhaustive Subset Enumeration & Global Optimality
* **Objective:** Verify that `BruteForce` evaluates every binary subset (2¹² = 4,096 baseline; 2¹³ = 8,192 revised—including candidates that fail one or more rules) and identifies the true optimal manifest.
* **JUnit Methods:**
  * `BruteForceTests.testBruteForceBaseline12Experiments()`
  * `BruteForceTests.testBruteForceRevisedRules13Experiments()`
  * `BruteForceTests.testBruteForceRevisedRulesConstraintEnforcement()`
  * `BruteForceTests.testBruteForceBreaksTiesByLighterWeight()`
* **Actual Result:**
  * **Baseline (`baseExperiments`, 12 items):** Checked all **`4,096`** subsets; identified optimal manifest `{1, 4, 5, 6, 8, 10, 11, 12}` at **`692.0 kg / 700.0 kg`** with maximum rating **`53`**.
  * **Revised Rules (`revisedExperiments`, 13 items):** Checked all **`8,192`** subsets; enforced solar cap, `11a` prerequisite, and `-7.0 kg` thermal credit; identified optimal manifest `{1, 4, 5, 6, 8, 10, 11, 11a, 12}` at **`695.0 kg / 700.0 kg`** with maximum rating **`58`**.
  * **Tie-Breaking:** When two valid subsets achieved identical total ratings, `BruteForce` ranked the lighter payload #1 (`OPTIMAL`).
* **Status:** **PASS**

---

## Section 4: Boundary & Edge Case Tests

### Test Case 5: Oversized Items & Identical Ratios
* **Objective:** Confirm graceful execution without exceptions or out-of-bounds selections under boundary inputs.
* **JUnit Methods:** `EdgeCaseTests.testMassiveIndividualWeightBypassedSafely()`, `EdgeCaseTests.testIdenticalRatiosResolvedCleanly()`

| Edge Case Scenario | Input Configuration | Expected Behavior | Actual Behavior | Status |
| :--- | :--- | :--- | :--- | :--- |
| **Massive Individual Weight** | Item `#99` (`800.0 kg`, Rating `10`) + valid items (`100.0 kg`, `200.0 kg`) at `700.0 kg` limit | Bypass `800.0 kg` item completely and select valid items (`300.0 kg`, Rating `3`) | Bypassed `#99`; selected valid items (`300.0 kg`, Rating `3`) | **PASS** |
| **Identical Ratios** | `10.0 kg / Rating 1` (`0.1000`) vs. `20.0 kg / Rating 2` (`0.1000`) at `20.0 kg` limit | Resolve tie deterministically without exceeding `20.0 kg` | Higher-rating tie-breaker selected `20.0 kg` item (`20.0 kg / 20.0 kg`, Rating `2`) | **PASS** |
