class ProjectileLauncher {
    ProjectileLauncher(int x, int y, int size, ControlPanel control, int xComponent, int yComponent, Attack attack, Pokemon... enemyPokemon) {
        String attackPath = attack.getAttackPath();
        boolean isEnemyAttack;
        isEnemyAttack = enemyPokemon.length > 0;
        switch(attackPath) {
            case "Linear":
                if (isEnemyAttack) {
                    ControlPanel.enemyProjectilesToAdd.add(new LinearProjectile(x, y, size, control, xComponent, yComponent, attack, enemyPokemon));
                } else {
                    ControlPanel.playerProjectilesToAdd.add(new LinearProjectile(x, y, size, control, xComponent, yComponent, attack, enemyPokemon));
                }
                break;
            case "Radial":
                if (isEnemyAttack) {
                    ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 2) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(3 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(0) * attack.getProjectileSpeed()), (int) (Math.sin(0) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(Math.PI) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(5 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(5 * Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(3 * Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 2) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(7 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(7 * Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                } else {
                    ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI / 2) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(3 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(0) * attack.getProjectileSpeed()), (int) (Math.sin(0) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(Math.PI) * attack.getProjectileSpeed()), (int) (Math.sin(Math.PI) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(5 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(5 * Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(3 * Math.PI / 2) * attack.getProjectileSpeed()), (int) (Math.sin(3 * Math.PI / 2) * attack.getProjectileSpeed()), attack, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new RadialProjectile(x, y, size, control, (int) (Math.cos(7 * Math.PI / 4) * attack.getProjectileSpeed()), (int) (Math.sin(7 * Math.PI / 4) * attack.getProjectileSpeed()), attack, enemyPokemon));
                }
                break;
            case "Boomerang":
                if (isEnemyAttack) {
                    ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 0, enemyPokemon));
                    ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 1, enemyPokemon));
                    ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 2, enemyPokemon));
                    ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 3, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 4, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 5, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 6, enemyPokemon));
                    //ControlPanel.enemyProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 7, enemyPokemon));

                } else {
                    ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 0, enemyPokemon));
                    ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 1, enemyPokemon));
                    ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 2, enemyPokemon));
                    ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 3, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 4, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 5, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 6, enemyPokemon));
                    //ControlPanel.playerProjectilesToAdd.add(new BoomerangProjectile(x, y, size, attack, control, 7, enemyPokemon));
                }
                break;
            case "Oscillate":
                if (isEnemyAttack) {
                    ControlPanel.enemyProjectilesToAdd.add(new OscillatingProjectile(x, y, size, attack, control, enemyPokemon));
                } else {
                    ControlPanel.playerProjectilesToAdd.add(new OscillatingProjectile(x, y, size, attack, control, enemyPokemon));
                }
                break;
            case "Homing":
                if (isEnemyAttack) {
                    ControlPanel.enemyProjectilesToAdd.add(new HomingProjectile(x, y, size, attack, control, enemyPokemon));
                } else {
                    ControlPanel.playerProjectilesToAdd.add(new HomingProjectile(x, y, size, attack, control, enemyPokemon));
                }
                break;
            default:
                try {
                    throw new IndexOutOfBoundsException();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid type of attack path.");
                }
                break;
        }
    }
}
