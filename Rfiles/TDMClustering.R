######################################3
##  QUESTION 4
#########################################

library(tm)
bios <- Corpus(DirSource("~/workspace/TextAnalytics-HW2/classbios",encoding = "UTF-8"))
bios <- tm_map(bios, function(x) iconv(enc2utf8(x), sub = "byte"))
bios <- tm_map(bios, function(x) stripWhitespace(x))
TermDocumentMatrix(bios)
tdmAll <- TermDocumentMatrix(bios)
tdmAll <- weightBin(tdmAll)

tdmat <- as.data.frame(as.matrix(tdmAll))
colnames(tdmat) <- LETTERS[seq(1,26)]
#write.csv(tdm,'tdm.csv')
#inspect(tdmAll)
#nTerms(tdmAll)
#Heaps_plot(tdmAll,main="Heaps Plot - classbios",xlab="Text Size", ylab="Vocabulary Size")
library(proxy)
Jacky <- as.data.frame(as.matrix(1-dissimilarity(tdmAll, method = "Jaccard")))
Cosine <- as.data.frame(as.matrix(1-dissimilarity(tdmAll, method = "cosine")))
Match <- as.data.frame(as.matrix(1-dissimilarity(tdmAll, method = "simple matching")))
Dice <- as.data.frame(as.matrix(1-dissimilarity(tdmAll, method = "Dice")))
Overlap <- matrix(0,nrow=26, ncol=26)
for(i in 1:26){
  for(j in 1:26){
    x_int_y <- sum(as.matrix(tdmAll)[,i] == 1 & as.matrix(tdmAll)[,j] == 1)
    Overlap[i,j] <- x_int_y/min(sum(as.matrix(tdmAll)[,i]),sum(as.matrix(tdmAll)[,j]))
  }
}
Overlap <- as.data.frame(Overlap)
colnames(Overlap) <- c('1','10','11','12','13','14','15','16','17','18','19','2','20','21','22','23','24','25','26','3','4','5','6','7','8','9')
rownames(Overlap) <- c('1','10','11','12','13','14','15','16','17','18','19','2','20','21','22','23','24','25','26','3','4','5','6','7','8','9')

write.csv(Jacky,"jaccard.csv")
write.csv(Cosine,"cosine.csv")
write.csv(Match,"match.csv")
write.csv(Dice,"dice.csv")
write.csv(Overlap,"overlap.csv")

##REMOVE EVERYTHING!
rm(list=ls())

#############################3
## QUestion 5 HOOORAAYYYYYYY
############################


library(tm)
bios <- Corpus(DirSource("~/workspace/TextAnalytics-HW2/classbios",encoding = "UTF-8"))
bios <- tm_map(bios, function(x) iconv(enc2utf8(x), sub = "byte"))
bios <- tm_map(bios, function(x) stripWhitespace(x))
TermDocumentMatrix(bios)
tdmAll <- TermDocumentMatrix(bios)
tdmIDF <- weightTfIdf(tdmAll,normalize=TRUE)
Tmat <- as.data.frame(as.matrix(tdmAll))
Mmat <- as.data.frame(as.matrix(tdmIDF))

library(MASS)
Tsvd <- svd(Tmat)
U <- Tsvd$u
S <- diag(Tsvd$d)
V <- t(Tsvd$v)
##plot(diag(Tsvd$d))
plot(Tsvd$d,type="l",main="SCREE Plot for SVD")

U <- U[,1:3]
S <- S[1:3,1:3]
V <- V[1:3,]

D1 <- V
D2 <- sqrt(S)%*%V
D3 <- S%*%V

colnames(D1) <- colnames(Mmat)
colnames(D2) <- colnames(Mmat)
colnames(D3) <- colnames(Mmat)

hcluster <- function(matrix,title){
  par(mfrow=c(2,2))

  Tcomp <- hclust(dist(matrix),method="complete")
  Tcomp2 <- hclust(dist(matrix),method="single")
  Tcomp3 <- hclust(dist(matrix),method="centroid")
  Tcomp4 <- hclust(dist(matrix),method="average")
  #mtext(title, side = 3, line = -21, outer = TRUE)
  plot(Tcomp,main="Complete")
  plot(Tcomp2,main="Single Link")
  plot(Tcomp3,main="Centroid")
  plot(Tcomp4,main="Average")
}
hcluster(t(Tmat),"By Terms")
hcluster(t(Mmat),"By TfIDF")
hcluster(t(S),"By S")
hcluster(t(D1), "By D1")
hcluster(t(D2), "By D2")
hcluster(t(D3), "By D3")
