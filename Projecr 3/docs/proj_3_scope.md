# 1. Project Overview

Project Name: Programming Project 3: Optimal selection_PLO-CS-4

Project Goal: To implement, compare, and critically evaluate multiple algorithmic strategies to solve the classic knapsack optimization problem

Target Audience / Users: students not currently taking CS 2430

# 2. In-Scope (What the Project Will Do)


  ## Implement Three Greedy Strategies
  
  ### Highest Rating First
        Select experiments based on highest rating without exceeding 700 kg.
        Output: list of chosen experiments, total weight, and total rating.
      
  ### Lightest First
        Select experiments based on lightest weight without exceeding 700 kg.
        Output: list of chosen experiments, total weight, and total rating.
      
  ### Best Rating-to-Weight Ratio First
        Compute score = rating / weight for each experiment.
        Select experiments based on highest ratio while total weight ≤ 700 kg.
        Output: list of chosen experiments, total weight, and total rating.
        
  Clearly display results for all three strategies.
  
  ## Brute Force / Exhaustive Search

  With 12 items, there are 212 = 4096 possible subsets.

  Write code that:

    Generates all 4096 possible subsets of experiments.
    
    For each subset, computes total weight and total rating.
    
    Identifies and displays the three highest-rated valid subsets (total weight ≤ 700 kg), 
    clearly indicating which one is optimal.
    
    Outputs: chosen experiments, total weight, and total rating for each.

This gives the true best solution for comparison against the greedy strategies.
  
  
  ## Comparison Summary Output (to Support Report) 

    Add output summarizing:
      Results of all 3 greedy strategies 
      Result of the brute-force optimal 
      A brief printed summary identifying which strategies matched the optimal and which did not
  

  ## Team Report
      
          The report is a formal, readable team document aimed at students not taking this course. 
          It must include paragraph writing (not just bullet points) and it must clearly label all data tables.
          
          The report must include:
            
  ### Title Block  
                      Team name; team members; course (CS 2430); semester; project title.
  ### Introduction  
                      Explain the problem and why it is interesting/relevant.
  ### Methods / Approach
                      Briefly describe each strategy (rating-first, weight-first, ratio-first, brute-force, 
                      and DP if implemented).
                      Mention the language, tools, and libraries used.
                      Explain how you verified correctness (including at least one edge case or cross-check).
  ### Results
                      Present the chosen subsets and totals for each strategy.
                      Clearly identify which strategy gave the optimal result.
                      Include small tables if helpful.
  ### Discussion / Analysis
                      Compare greedy vs. brute-force results.
                      Explain why some greedy strategies miss the optimal.
                      Discuss scalability: brute force vs. dynamic programming.
                      Describe one other plausible approach (e.g., heuristic or priority-based) 
                      and why it might be viable.
  ### Conclusion  
                      Summarize what you learned about discrete optimization problems.
  ### Summary of Work Done / Lessons Learned – 
                      One short paragraph reflecting on what worked, 
                      what didn’t, what was most interesting, and how it helped your understanding.
  ### Sources  
                      Cite any external references you used.
      
      
  ## Screencast
  
        Record a screen-capture video, with narration (voice or captions), that shows and describes:
        
          Your project structure (including /docs, README, and key source files).
          
          Your implementation of the three greedy strategies and the brute-force approach (high-level walkthrough).
          
          Running the program to produce:
              All 3 greedy solutions
              The brute-force solution (including the top 3 valid subsets)
              
              Each team member must participate (voice or captions) and explain their contribution 
              (what they owned and how to find it in the zip/repo).
              
      Maximum length: 10 minutes.

# 4. Out-of-Scope (What the Project Will NOT Do)

Explicitly state what features are excluded to prevent scope creep:
      Excluded Features: 


# 5. Deliverables

  ## Team report 
      
          Team code package (.zip) — a zip exported from your repository containing:
            
              All source code files
              
              /docs folder (include at least the items required at checkpoint)
              
              README with build/run instructions
              
              CONTRIBUTIONS.md mapping team members to responsibilities and evidence pointers (PR/commit/file/function)
              
              Any data output files you generated (CSV/TSV/TXT) or a clearly labeled section in the report if you embed tables there
              

  ## Team screencast video (≤ 10 minutes)
      
            Show your code (high-level walkthrough)
            
            Demonstrate required runs/outputs
            
            Each team member must participate (voice or captions) and explain their contribution

# 6. Constraints

      Resource Constraints: Team size: 4
      Timeline Constraints: 
            Weekly Report 1 
                  Due Nov 1 at 11:59pm
            Weekly Report 2
                  Due Nov 8 at 11:59pm
            Checkpoint
                  Due Nov 8 at 11:59pm
            Weekly Report 3
                  Due Nov 15 at 11:59pm
            Peer Review
                  Due Nov 15 at 11:59pm
            Programming Project 3: Optimal selection_PLO-CS-4
                  Due Nov 15 at 11:59pm
      Language: Java
      Libraries/Frameworks: JUnit 5 for testing (need to verify with dom )
      Environment: [e.g., IntelliJ IDEA or Eclipse, running on Windows/macOS/Linux]
