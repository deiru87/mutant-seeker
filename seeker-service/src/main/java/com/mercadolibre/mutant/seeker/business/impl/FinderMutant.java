package com.mercadolibre.mutant.seeker.business.impl;

import com.mercadolibre.mutant.seeker.business.MutantChecker;
import com.mercadolibre.shared.dto.SeekerServiceRequest;
import com.mercadolibre.shared.enums.DnaWordsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class That contain business logic to validate if a dna sequences is mutant.
 * It's use for V1 of services mutant
 * @Author: Deimer Ballesteros
 */
@Component
public class FinderMutant implements MutantChecker {


    private static Logger log = LoggerFactory.getLogger(DelegateImpl.class);
    /**
     * Method that validate if an array of string correspond with a mutant
     *
     * @param seekerServiceRequest - Request with sequence dna
     * @return boolean that represent if sequence is mutant or not
     */
    @Override
    public Mono<Boolean> validateMutant(Mono<SeekerServiceRequest> seekerServiceRequest) {
        return seekerServiceRequest.map(request -> {
            String[] dna = new String[request.getDna().size()];
            dna = request.getDna().toArray(dna);
            log.debug("checking is dna sequence {} to know if is mutant", Arrays.toString(dna));
            return isMutant(dna);
        });


    }


    /**
     * Method that search in a matrixNxN represented by an array of string if
     * there is mutant sequence. This method search each element in the matrix
     * until match with the first element in the sequences allow. Sequences that iterate
     * one by one (AAAA, CCCC, TTTT, GGGG). if one element in a matrix match with the first
     * in any sequence allowed then searched in the around positions of the element in the matrix
     * (Only the valid positions) if found other element that match with the first match then continued
     * searching in the direction that mark the second match. If found 4 character equals then check one sequence
     * then save the path in the matrix to avoid check the same sequence of element in the next iterations. When find
     * more than one sequence with letter equals return with boolean value true if not return false.
     * @param dna
     * @return boolean - mean if is mutant or not
     */
     private boolean isMutant(String[] dna){

        ConcurrentHashMap<Integer, Integer> pathsFound = new ConcurrentHashMap<Integer, Integer>();
        ConcurrentHashMap<Integer, Integer> tmpFound = new ConcurrentHashMap<Integer, Integer>();
        //Arrays with postion to sum in each element in a matrix according postion X or Y
        int[] x = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] y = {-1, 0, 1, -1, 1, -1, 0, 1};
        int amountFound = 0;

        //iterate in enum with all allowed values of sequences
        for(DnaWordsEnum dnaWordsEnum: DnaWordsEnum.values()){
            String word = dnaWordsEnum.getWord();
            //clear HashMap with information of path found with sequences already analyzed
            pathsFound.clear();
            tmpFound.clear();
            //iterate oin all matrix
            for(int i =0; i < dna.length; i++){
                for(int j =0; j < dna[i].length(); j++){
                    int n = 0;
                    //validate if one element is equal to first element in reference sequence
                    if(word.charAt(n) == dna[i].charAt(j)){
                        int lettersFound=1;
                        n = 1;
                        //if find one element that match start to iterate in the 8 positions around that element
                        for(int z=0; z<x.length; z++){
                            int xPos = i + x[z];
                            int yPos = j + y[z];
                            //Validate for position allows according location in the matrix
                            if(( xPos > -1 && xPos < dna[i].length()) && (yPos > -1 && yPos < dna[i].length())){
                                //validate that not iterate over elements already iterated to find element repeats in sequence
                                //And validate if found other element with same character that first element found
                                if(dna[xPos].charAt(yPos) != word.charAt(n) || (pathsFound.containsKey(xPos) && pathsFound.containsValue(yPos))){
                                    lettersFound = 1;
                                    continue;
                                }
                                //increment letter found to know when letter reach 4
                                lettersFound += 1;
                                //save position of element found temporally
                                tmpFound.put(i, j);
                                tmpFound.put(xPos, yPos);
                                //define position x and y to doing movement in the direction define
                                int idxPos = xPos + x[z];
                                int idyPos = yPos + y[z];
                                //iterate over letters remaining to reach a sequence
                                for(int wordPos = word.length() - lettersFound; wordPos < word.length(); wordPos++){
                                    //Validate for position allows according location in the matrix
                                    if((idxPos > -1 && idxPos < dna[i].length()) && (idyPos > -1 && idyPos < dna[i].length())){
                                        //validate if other element match
                                        if(dna[idxPos].charAt(idyPos) == word.charAt(lettersFound)){
                                            //increment letter found and save position and define x and y position
                                            lettersFound += 1;
                                            tmpFound.put(idxPos, idyPos);
                                            idxPos += x[z];
                                            idyPos += y[z];
                                        }else{
                                            //if the direction not found elements repeats en sequence mark letter found like 1 and again start
                                            lettersFound = 1;
                                            tmpFound.clear();
                                            break;
                                        }
                                        //validate if letter found is equal to 4 and save one sequence found and mark path definitive found
                                        //the rest is clean
                                        if(lettersFound == word.length()){
                                            amountFound += 1;
                                            lettersFound = 1;
                                            pathsFound.putAll(tmpFound);
                                            tmpFound.clear();
                                            break;
                                        }
                                    }

                                }

                            }
                        }
                        //if amount of sequence rise 1 return true. Means that is mutant
                        if(amountFound > 1){
                            return true;
                        }

                    }
                }
            }

        }

        return false;
    }





}
