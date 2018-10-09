/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.help.HelpMap;
/*     */ import org.bukkit.help.HelpTopic;
/*     */ import org.bukkit.util.ChatPaginator;
/*     */ import org.bukkit.util.ChatPaginator.ChatPage;
/*     */ 
/*     */ public class HelpCommand extends VanillaCommand
/*     */ {
/*     */   public HelpCommand()
/*     */   {
/*  29 */     super("help");
/*  30 */     this.description = "Shows the help menu";
/*  31 */     this.usageMessage = "/help <pageNumber>\n/help <topic>\n/help <topic> <pageNumber>";
/*  32 */     setAliases(Arrays.asList(new String[] { "?" }));
/*  33 */     setPermission("bukkit.command.help");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  38 */     if (!testPermission(sender)) { return true;
/*     */     }
/*     */     
/*     */     int pageNumber;
/*     */     
/*     */     String command;
/*     */     int pageNumber;
/*  45 */     if (args.length == 0) {
/*  46 */       String command = "";
/*  47 */       pageNumber = 1;
/*  48 */     } else if (NumberUtils.isDigits(args[(args.length - 1)])) {
/*  49 */       String command = StringUtils.join(ArrayUtils.subarray(args, 0, args.length - 1), " ");
/*     */       int pageNumber;
/*  51 */       try { pageNumber = NumberUtils.createInteger(args[(args.length - 1)]).intValue();
/*     */       } catch (NumberFormatException localNumberFormatException) { int pageNumber;
/*  53 */         pageNumber = 1;
/*     */       }
/*  55 */       if (pageNumber <= 0) {
/*  56 */         pageNumber = 1;
/*     */       }
/*     */     } else {
/*  59 */       command = StringUtils.join(args, " ");
/*  60 */       pageNumber = 1; }
/*     */     int pageWidth;
/*     */     int pageHeight;
/*  63 */     int pageWidth; if ((sender instanceof ConsoleCommandSender)) {
/*  64 */       int pageHeight = Integer.MAX_VALUE;
/*  65 */       pageWidth = Integer.MAX_VALUE;
/*     */     } else {
/*  67 */       pageHeight = 9;
/*  68 */       pageWidth = 55;
/*     */     }
/*     */     
/*  71 */     HelpMap helpMap = Bukkit.getServer().getHelpMap();
/*  72 */     HelpTopic topic = helpMap.getHelpTopic(command);
/*     */     
/*  74 */     if (topic == null) {
/*  75 */       topic = helpMap.getHelpTopic("/" + command);
/*     */     }
/*     */     
/*  78 */     if (topic == null) {
/*  79 */       topic = findPossibleMatches(command);
/*     */     }
/*     */     
/*  82 */     if ((topic == null) || (!topic.canSee(sender))) {
/*  83 */       sender.sendMessage(ChatColor.RED + "No help for " + command);
/*  84 */       return true;
/*     */     }
/*     */     
/*  87 */     ChatPaginator.ChatPage page = ChatPaginator.paginate(topic.getFullText(sender), pageNumber, pageWidth, pageHeight);
/*     */     
/*  89 */     StringBuilder header = new StringBuilder();
/*  90 */     header.append(ChatColor.YELLOW);
/*  91 */     header.append("--------- ");
/*  92 */     header.append(ChatColor.WHITE);
/*  93 */     header.append("Help: ");
/*  94 */     header.append(topic.getName());
/*  95 */     header.append(" ");
/*  96 */     if (page.getTotalPages() > 1) {
/*  97 */       header.append("(");
/*  98 */       header.append(page.getPageNumber());
/*  99 */       header.append("/");
/* 100 */       header.append(page.getTotalPages());
/* 101 */       header.append(") ");
/*     */     }
/* 103 */     header.append(ChatColor.YELLOW);
/* 104 */     for (int i = header.length(); i < 55; i++) {
/* 105 */       header.append("-");
/*     */     }
/* 107 */     sender.sendMessage(header.toString());
/*     */     
/* 109 */     sender.sendMessage(page.getLines());
/*     */     
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*     */   {
/* 116 */     Validate.notNull(sender, "Sender cannot be null");
/* 117 */     Validate.notNull(args, "Arguments cannot be null");
/* 118 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 120 */     if (args.length == 1) {
/* 121 */       List<String> matchedTopics = new ArrayList();
/* 122 */       String searchString = args[0];
/* 123 */       for (HelpTopic topic : Bukkit.getServer().getHelpMap().getHelpTopics()) {
/* 124 */         String trimmedTopic = topic.getName().startsWith("/") ? topic.getName().substring(1) : topic.getName();
/*     */         
/* 126 */         if (trimmedTopic.startsWith(searchString)) {
/* 127 */           matchedTopics.add(trimmedTopic);
/*     */         }
/*     */       }
/* 130 */       return matchedTopics;
/*     */     }
/* 132 */     return ImmutableList.of();
/*     */   }
/*     */   
/*     */   protected HelpTopic findPossibleMatches(String searchString) {
/* 136 */     int maxDistance = searchString.length() / 5 + 3;
/* 137 */     Set<HelpTopic> possibleMatches = new TreeSet(org.bukkit.help.HelpTopicComparator.helpTopicComparatorInstance());
/*     */     
/* 139 */     if (searchString.startsWith("/")) {
/* 140 */       searchString = searchString.substring(1);
/*     */     }
/*     */     
/* 143 */     for (HelpTopic topic : Bukkit.getServer().getHelpMap().getHelpTopics()) {
/* 144 */       String trimmedTopic = topic.getName().startsWith("/") ? topic.getName().substring(1) : topic.getName();
/*     */       
/* 146 */       if (trimmedTopic.length() >= searchString.length())
/*     */       {
/*     */ 
/*     */ 
/* 150 */         if (Character.toLowerCase(trimmedTopic.charAt(0)) == Character.toLowerCase(searchString.charAt(0)))
/*     */         {
/*     */ 
/*     */ 
/* 154 */           if (damerauLevenshteinDistance(searchString, trimmedTopic.substring(0, searchString.length())) < maxDistance)
/* 155 */             possibleMatches.add(topic);
/*     */         }
/*     */       }
/*     */     }
/* 159 */     if (possibleMatches.size() > 0) {
/* 160 */       return new org.bukkit.help.IndexHelpTopic("Search", null, null, possibleMatches, "Search for: " + searchString);
/*     */     }
/* 162 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static int damerauLevenshteinDistance(String s1, String s2)
/*     */   {
/* 176 */     if ((s1 == null) && (s2 == null)) {
/* 177 */       return 0;
/*     */     }
/* 179 */     if ((s1 != null) && (s2 == null)) {
/* 180 */       return s1.length();
/*     */     }
/* 182 */     if ((s1 == null) && (s2 != null)) {
/* 183 */       return s2.length();
/*     */     }
/*     */     
/* 186 */     int s1Len = s1.length();
/* 187 */     int s2Len = s2.length();
/* 188 */     int[][] H = new int[s1Len + 2][s2Len + 2];
/*     */     
/* 190 */     int INF = s1Len + s2Len;
/* 191 */     H[0][0] = INF;
/* 192 */     for (int i = 0; i <= s1Len; i++) {
/* 193 */       H[(i + 1)][1] = i;
/* 194 */       H[(i + 1)][0] = INF;
/*     */     }
/* 196 */     for (int j = 0; j <= s2Len; j++) {
/* 197 */       H[1][(j + 1)] = j;
/* 198 */       H[0][(j + 1)] = INF;
/*     */     }
/*     */     
/* 201 */     Map<Character, Integer> sd = new HashMap();
/* 202 */     char[] arrayOfChar; int i = (arrayOfChar = (s1 + s2).toCharArray()).length; for (int j = 0; j < i; j++) { char Letter = arrayOfChar[j];
/* 203 */       if (!sd.containsKey(Character.valueOf(Letter))) {
/* 204 */         sd.put(Character.valueOf(Letter), Integer.valueOf(0));
/*     */       }
/*     */     }
/*     */     
/* 208 */     for (int i = 1; i <= s1Len; i++) {
/* 209 */       int DB = 0;
/* 210 */       for (int j = 1; j <= s2Len; j++) {
/* 211 */         int i1 = ((Integer)sd.get(Character.valueOf(s2.charAt(j - 1)))).intValue();
/* 212 */         int j1 = DB;
/*     */         
/* 214 */         if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
/* 215 */           H[(i + 1)][(j + 1)] = H[i][j];
/* 216 */           DB = j;
/*     */         } else {
/* 218 */           H[(i + 1)][(j + 1)] = (Math.min(H[i][j], Math.min(H[(i + 1)][j], H[i][(j + 1)])) + 1);
/*     */         }
/*     */         
/* 221 */         H[(i + 1)][(j + 1)] = Math.min(H[(i + 1)][(j + 1)], H[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1));
/*     */       }
/* 223 */       sd.put(Character.valueOf(s1.charAt(i - 1)), Integer.valueOf(i));
/*     */     }
/*     */     
/* 226 */     return H[(s1Len + 1)][(s2Len + 1)];
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\HelpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */